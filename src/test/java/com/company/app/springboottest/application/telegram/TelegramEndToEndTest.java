package com.company.app.springboottest.application.telegram;

import com.company.app.springboottest.application.SpringBootTestApplicationContext;
import com.company.app.telegram.binder.BinderContainer;
import com.company.app.telegram.binder.impl.TelegramOffBinderImpl;
import com.company.app.telegram.controller.ChatController;
import com.company.app.telegram.controller.TelegramController;
import com.company.app.telegram.domain.dto.ChatDto;
import com.company.app.telegram.domain.entity.Chat;
import com.company.app.telegram.domain.entity.History;
import com.company.app.telegram.domain.entity.UserInfo;
import com.company.app.telegram.domain.repository.ChatRepository;
import com.company.app.telegram.domain.service.api.ChatService;
import com.company.app.telegram.domain.util.ChatUtil;
import org.hibernate.ObjectNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.List;

class TelegramEndToEndTest extends SpringBootTestApplicationContext {

    @Autowired
    TelegramController telegramController;
    @Autowired
    ChatController chatController;
    @Autowired
    ChatService chatService;
    @Autowired
    TelegramOffBinderImpl telegramBinder;
    @Autowired
    ChatRepository chatRepository;

    @BeforeEach
    void init() {
        chatRepository.deleteAll();
    }

    @Test
    void telegram_chat_history_test() {
        ResponseEntity<Long> chatId = chatController.create(ChatDto.builder()
                .chatName("653606407")
                .enableNotifications(true)
                .build());
        telegramController.say("test_text");

        Chat chat = chatService.read(chatId.getBody());
        List<History> historyList = chat.getHistoryList();

        Assertions.assertNotNull(historyList);
        Assertions.assertEquals(1, historyList.size());
        Assertions.assertEquals("test_text", historyList.get(0).getMessage());
    }

    @Test
    void chatController_crud_test() {
        ChatDto chatDto = ChatDto.builder().chatName("653606407").build();
        Long id = chatController.create(chatDto).getBody();
        Assertions.assertEquals(1, chatRepository.findAll().size());

        chatDto.setUserInfo(UserInfo.builder().role("Test_Role").build());
        chatController.update(id, chatDto);
        Assertions.assertEquals(1, chatRepository.findAll().size());

        Chat after = chatController.read(id).getBody();
        Assertions.assertEquals("Test_Role", after.getUserInfo().getRole());

        chatController.delete(id);
        Assertions.assertEquals(0, chatRepository.findAll().size());
        Assertions.assertThrows(ObjectNotFoundException.class, () -> chatController.read(id));
    }

    @Test
    void history_list_is_empty_if_enableNotifications_false() {
        ChatDto chatDto = ChatDto.builder().chatName("653606407").build();
        Long id = chatController.create(chatDto).getBody();

        telegramController.say("test_text");
        Chat chat = chatController.read(id).getBody();

        List<History> historyList = chat.getHistoryList();
        Assertions.assertEquals(0, historyList.size());
    }

    @Test
    void telegram_binder_can_deactivate_notifications() {
        List<Chat> all = chatRepository.findAll();
        Assertions.assertEquals(0, all.size());

        ChatDto chatDto = ChatDto.builder().chatName("653606407").enableNotifications(true).build();
        Long id = chatController.create(chatDto).getBody();
        Chat chat = ChatUtil.of(chatDto);
        chat.setId(id);

        telegramBinder.bind(BinderContainer.builder()
                .chat(chat)
                .message("TG")
                .build());

        Chat after = chatController.read(id).getBody();
        Assertions.assertFalse(after.isEnableNotifications());
    }
}