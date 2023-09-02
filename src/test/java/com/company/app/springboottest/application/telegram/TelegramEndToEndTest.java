package com.company.app.springboottest.application.telegram;

import com.company.app.core.SpringBootTestApplicationContext;
import com.company.app.telegram.component.binder.BinderContext;
import com.company.app.telegram.component.binder.impl.TelegramOffBinder;
import com.company.app.telegram.controller.ChatController;
import com.company.app.telegram.controller.TelegramController;
import com.company.app.telegram.domain.dto.ChatDto;
import com.company.app.telegram.domain.entity.Chat;
import com.company.app.telegram.domain.entity.History;
import com.company.app.telegram.domain.entity.UserInfo;
import com.company.app.telegram.domain.mapper.Mapper;
import com.company.app.telegram.domain.repository.ChatRepository;
import com.company.app.telegram.domain.repository.UserInfoRepository;
import com.company.app.telegram.domain.service.ChatService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.ObjectNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

@Slf4j
class TelegramEndToEndTest extends SpringBootTestApplicationContext {

    @Autowired
    private TelegramController telegramController;
    @Autowired
    private ChatController chatController;
    @Autowired
    private ChatService chatService;
    @Autowired
    private TelegramOffBinder telegramBinder;
    @Autowired
    private ChatRepository chatRepository;
    @Autowired
    private UserInfoRepository userInfoRepository;

//    @BeforeEach
//    void init() {
//        chatRepository.deleteAll();
//    }

//    @Test
//    void telegram_chat_history_test() {
//        ResponseEntity<Long> chatId = chatController.create(ChatDto.builder()
//                .chatName("653606407")
//                .enableNotifications(true)
//                .build());
//        telegramController.say("test_text");
//
//        Chat chat = chatService.read(chatId.getBody());
//        List<History> historyList = chat.getHistoryList();
//
//        Assertions.assertNotNull(historyList);
//        Assertions.assertEquals(1, historyList.size());
//        Assertions.assertEquals("test_text", historyList.get(0).getMessage());
//    }
//
//    @Test
//    void chatController_crud_test() {
//        String chatName = "1111111111";
//        ChatDto chatDto = ChatDto.builder().chatName(chatName).build();
//        Long id = chatController.create(chatDto).getBody();
//        Assertions.assertEquals(chatName, chatRepository.findFirstByChatName(chatName).get().getChatName());
//
//        String role = "test_admin_role";
//        chatDto.setUserInfo(UserInfo.builder().role(role).build());
//        chatController.update(id, chatDto);
//        Assertions.assertNotNull(userInfoRepository.findByRole(role).get().getRole());
//
//        Chat after = chatController.read(id).getBody();
//        Assertions.assertEquals("Test_Role", after.getUserInfo().getRole());
//
//        chatController.delete(id);
//        Assertions.assertEquals(0, chatRepository.findAll().size());
//        Assertions.assertThrows(ObjectNotFoundException.class, () -> chatController.read(id));
//    }
//
//    @Test
//    void history_list_is_empty_if_enableNotifications_false() {
//        ChatDto chatDto = ChatDto.builder().chatName("653606407").build();
//        Long id = chatController.create(chatDto).getBody();
//
//        telegramController.say("test_text");
//        Chat chat = chatController.read(id).getBody();
//
//        List<History> historyList = chat.getHistoryList();
//        Assertions.assertEquals(0, historyList.size());
//    }
//
//    @Test
//    void telegram_binder_can_deactivate_notifications() {
//        List<Chat> all = chatRepository.findAll();
//        Assertions.assertEquals(0, all.size());
//
//        ChatDto chatDto = ChatDto.builder().chatName("653606407").enableNotifications(true).build();
//        Long id = chatController.create(chatDto).getBody();
//        Chat chat = Mapper.of(chatDto);
//        chat.setId(id);
//
//        telegramBinder.bind(BinderContext.builder()
//                .chat(chat)
//                .message("TG")
//                .build());
//
//        Chat after = chatController.read(id).getBody();
//        Assertions.assertFalse(after.isEnableNotifications());
//    }
//
//    @Test
//    void chatController_crud_test2() {
//        List<ChatDto> chatDtoList = new ArrayList<>();
//
//        for (int i = 0; i < 10; i++) {
//            ChatDto chatDto = ChatDto.builder().chatName("_" + i).build();
//            chatController.create(chatDto);
//            chatDtoList.add(chatDto);
//        }
//        log.debug("ready *****");
//        Assertions.assertEquals(10, chatDtoList.size());
//        List<Chat> all = chatRepository.findAll();
//
//        Assertions.assertEquals(10, all.size());
//        all.forEach(chat -> log.debug(chat.getChatName()));
//    }

}