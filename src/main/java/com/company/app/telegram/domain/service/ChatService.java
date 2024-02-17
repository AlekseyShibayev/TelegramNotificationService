package com.company.app.telegram.domain.service;

import com.company.app.common.timer.domain.enums.ActionType;
import com.company.app.common.timer.service.TimerService;
import com.company.app.telegram.domain.entity.Chat;
import com.company.app.telegram.domain.entity.Mode;
import com.company.app.telegram.domain.entity.UserInfo;
import com.company.app.telegram.domain.enums.ModeType;
import com.company.app.telegram.domain.enums.Role;
import com.company.app.telegram.domain.model.UpdateChat;
import com.company.app.telegram.domain.repository.ChatRepository;
import com.company.app.telegram.domain.repository.ModeRepository;
import com.company.app.telegram.domain.repository.UserInfoRepository;
import com.company.app.telegram.domain.spec.ChatSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.company.app.telegram.domain.enums.ModeType.DEFAULT;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final UserInfoRepository userInfoRepository;
    private final ModeRepository modeRepository;
    private final TimerService timerService;

    public Chat findChatByChatNameOrCreateIfNotExist(String chatName) {
        return chatRepository.findOne(ChatSpecification.chatNameIs(chatName))
                .orElseGet(() -> createChat(chatName));
    }

    private Chat createChat(String chatName) {
        UserInfo userInfo = new UserInfo();
        userInfo.setRole(Role.USER.name());
        UserInfo persistedUserInfo = userInfoRepository.save(userInfo);
        Chat chat = new Chat()
            .setChatName(chatName)
            .setUserInfo(persistedUserInfo)
            .setMode(modeRepository.findByType(DEFAULT));
        return chatRepository.save(chat);
    }

    public void updateChat(UpdateChat updateChat) {
        Optional<Chat> optional = chatRepository.findByChatName(updateChat.getChatName());
        if (optional.isEmpty()) {
            return;
        }

        Chat chat = optional.get();

        if (updateChat.getMode() != null) {
            String mode = updateChat.getMode();
            ModeType modeType = ModeType.valueOf(mode);

            if (modeType.typeOf(chat)) {
                log.debug("chat with name [{}] already in mode [{}]", chat.getChatName(), modeType);
            } else if (modeType.equals(DEFAULT)) {
                timerService.stop(chat.getChatName(), ActionType.ROLLBACK_CHAT_MODE_TO_DEFAULT);
                updateChatMode(chat, ModeType.DEFAULT);
            } else if (modeType.equals(ModeType.ADD_DESIRE)) {
                timerService.start(chat.getChatName(), ActionType.ROLLBACK_CHAT_MODE_TO_DEFAULT);
                updateChatMode(chat, ModeType.ADD_DESIRE);
            } else {
                log.error("can not update chat with name [{}] by mode [{}], not support yet", chat.getChatName(), modeType);
            }
        }

    }

    public void updateChatMode(Chat chat, ModeType modeType) {
        Mode newMode = modeRepository.findByType(modeType);
        chat.setMode(newMode);
        chatRepository.save(chat);
    }

}