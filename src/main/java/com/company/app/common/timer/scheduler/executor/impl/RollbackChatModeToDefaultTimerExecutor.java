package com.company.app.common.timer.scheduler.executor.impl;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.company.app.common.entity_finder.model.PersistenceContext;
import com.company.app.common.timer.domain.entity.Timer;
import com.company.app.common.timer.domain.enums.ActionType;
import com.company.app.common.timer.domain.enums.StatusType;
import com.company.app.common.timer.domain.repository.TimerRepository;
import com.company.app.common.timer.scheduler.executor.TimerExecutor;
import com.company.app.infrastructure.jpa.entityfinder.EntityFinder;
import com.company.app.infrastructure.jpa.entityfinder.model.CommonQuery;
import com.company.app.telegram.TelegramFacade;
import com.company.app.telegram.domain.entity.Chat;
import com.company.app.telegram.domain.entity.Chat_;
import com.company.app.telegram.domain.entity.IncomingMessageTask;
import com.company.app.telegram.domain.entity.Mode;
import com.company.app.telegram.domain.enums.ModeType;
import com.company.app.telegram.domain.repository.IncomingMessageTaskRepository;
import com.company.app.telegram.domain.service.ChatService;
import com.company.app.telegram.domain.spec.ChatSpecification;
import com.company.app.telegram.domain.spec.IncomingMessageTaskSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
public class RollbackChatModeToDefaultTimerExecutor implements TimerExecutor {

    @Value("${timer.rollback_chat_mode_to_default.timeout_minutes}")
    private Integer timeout;
    private static final ActionType ACTION_TYPE = ActionType.ROLLBACK_CHAT_MODE_TO_DEFAULT;

    private final ChatService chatService;
    private final TimerRepository timerRepository;
    private final IncomingMessageTaskRepository incomingMessageTaskRepository;
    private final EntityFinder entityFinder;
    private final TelegramFacade telegramFacade;

    @Override
    public ActionType getType() {
        return ACTION_TYPE;
    }

    /**
     * time: _______create______now_____create + minutes_____
     * time: _______create______create + minutes_______now_____
     */
    @Transactional
    public void execute() {
        List<Timer> filteredTimers = getFilteredTimers();
        Map<String, Chat> chatMap = getChatMap(filteredTimers);

        log.trace("try execute for [{}] timers and [{}] chats", filteredTimers.size(), chatMap.size());
        filteredTimers.forEach(timer -> forOne(chatMap, timer));
    }

    private List<Timer> getFilteredTimers() {
        ZonedDateTime now = ZonedDateTime.now();

        List<Timer> allTimers = timerRepository.findAllByActionTypeAndStatusType(ACTION_TYPE, StatusType.NEW);
        // todo do date filter in bd
        return allTimers.stream()
            .filter(timer -> timer.getCreateDate().plusMinutes(timeout).isBefore(now))
            .toList();
    }

    private Map<String, Chat> getChatMap(List<Timer> allTimers) {
        Set<String> chatNames = allTimers.stream()
            .map(Timer::getEntityView)
            .collect(Collectors.toSet());

        List<Chat> chatList = entityFinder.findAllAsList(new CommonQuery<>(Chat.class)
            .setSpecification(ChatSpecification.chatNameIn(chatNames))
            .with(Chat_.MODE)
        );

        return chatList.stream()
            .collect(Collectors.toMap(Chat::getChatName, Function.identity()));
    }

    private void forOne(Map<String, Chat> chatMap, Timer timer) {
        String chatName = timer.getEntityView();
        if (chatMap.containsKey(chatName)) {
            rollbackChatModeToDefault(chatMap.get(chatName), timer, chatName);
        } else {
            timer.setStatusType(StatusType.FAIL);
            timerRepository.save(timer);
        }
    }

    private void rollbackChatModeToDefault(Chat chat, Timer timer, String chatName) {
        Mode mode = chat.getMode();

        if (!mode.getType().equals(ModeType.DEFAULT.name())) {
            chatService.updateChatMode(chat, ModeType.DEFAULT);

            List<IncomingMessageTask> tasks = incomingMessageTaskRepository.findAll(IncomingMessageTaskSpecification.chatNameIs(chatName)
                .and(IncomingMessageTaskSpecification.modeTypeIs(ModeType.ADD_DESIRE.name())));
            incomingMessageTaskRepository.deleteAll(tasks);

            timer.setStatusType(StatusType.DONE);
            timerRepository.save(timer);

            telegramFacade.writeToTargetChat(chatName, "Прошло %s минуты. Вы не успели. Операция отменена.".formatted(timeout));
        }
    }

}