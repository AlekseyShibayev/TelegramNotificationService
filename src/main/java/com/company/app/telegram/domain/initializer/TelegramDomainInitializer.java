package com.company.app.telegram.domain.initializer;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.company.app.common.tool.json.JsonMapper;
import com.company.app.telegram.domain.entity.Chat;
import com.company.app.telegram.domain.entity.Mode;
import com.company.app.telegram.domain.entity.Subscription;
import com.company.app.telegram.domain.enums.ModeType;
import com.company.app.telegram.domain.repository.ChatRepository;
import com.company.app.telegram.domain.repository.ModeRepository;
import com.company.app.telegram.domain.repository.SubscriptionRepository;
import com.company.app.telegram.integration.in.button.model.ButtonCallbackAction;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Initializes different @Entity, when application started.
 */
@Service
@RequiredArgsConstructor
public class TelegramDomainInitializer {

    @Value("classpath:telegram/init_chat.json")
    private Resource resource;

    private final JsonMapper<Chat> jsonTool;
    private final ChatRepository chatRepository;
    private final ModeRepository modeRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final List<ButtonCallbackAction> binderList;

    @EventListener({ContextRefreshedEvent.class})
    @Transactional
    public void init() {
        prepareMode();
        prepareChat();
        prepareSubscriptions();
    }

    private void prepareChat() {
        Mode defaultMode = modeRepository.findByType(ModeType.DEFAULT);

        List<Chat> currentChats = chatRepository.findAll();
        Map<String, Chat> chatNameVsChat = currentChats.stream().collect(Collectors.toMap(Chat::getChatName, Function.identity()));

        List<Chat> list = jsonTool.toJavaAsList(resource, Chat.class);
        List<Chat> newChats = list.stream()
            .filter(chat -> isAbsentInDataBase(chatNameVsChat, chat))
            .map(chat -> chat.setMode(defaultMode))
            .toList();
        chatRepository.saveAll(newChats);
    }

    private boolean isAbsentInDataBase(Map<String, Chat> chatNameVsChat, Chat chat) {
        return !chatNameVsChat.containsKey(chat.getChatName());
    }

    private void prepareMode() {
        List<Mode> currentModes = modeRepository.findAll();
        Map<String, Mode> typeVsNode = currentModes.stream().collect(Collectors.toMap(Mode::getType, Function.identity()));

        List<Mode> list = Arrays.stream(ModeType.values())
            .map(newModeType -> new Mode().setType(newModeType.name()))
            .filter(mode -> !typeVsNode.containsKey(mode.getType()))
            .toList();

        modeRepository.saveAll(list);
    }

    void prepareSubscriptions() {
        List<Subscription> currentSubscriptions = subscriptionRepository.findAll();
        Map<String, Subscription> typeVsSubscription = currentSubscriptions.stream()
            .collect(Collectors.toMap(Subscription::getType, Function.identity()));

        List<Subscription> subscriptionList = binderList.stream()
            .map(binderType -> new Subscription().setType(binderType.getType()))
            .filter(subscription -> !typeVsSubscription.containsKey(subscription.getType()))
            .toList();
        subscriptionRepository.saveAll(subscriptionList);
    }

}