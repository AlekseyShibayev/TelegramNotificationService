package com.company.app.springboottest.application.telegram;

import com.company.app.core.SpringBootTestApplicationContext;
import com.company.app.telegram.controller.TelegramController;
import com.company.app.telegram.controller.TelegramOwnerController;
import com.company.app.telegram.domain.dto.ChatDto;
import com.company.app.telegram.domain.entity.History;
import com.company.app.telegram.domain.repository.ChatRepository;
import com.company.app.telegram.domain.service.InitialChatRegistry;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.Collectors;

class TelegramOwnerControllerEndToEndTest extends SpringBootTestApplicationContext {

    private static final String OWNER_MESSAGE = "owner here";

    @Autowired
    TelegramOwnerController telegramOwnerController;
    @Autowired
    TelegramController telegramController;
    @Autowired
    InitialChatRegistry initialChatRegistry;
    @Autowired
    ChatRepository chatRepository;

    @BeforeEach
    void init() {
        chatRepository.deleteAll();
    }

    @SneakyThrows
    @Test
    void telegram_owner_get_all_test() {
        initialChatRegistry.init();
        telegramController.say(OWNER_MESSAGE);

        ResponseEntity<List<ChatDto>> json = telegramOwnerController.getAllTelegramSettingsAsJson();
        List<ChatDto> chatDtoList = json.getBody();
        ChatDto chatDto = chatDtoList.get(0);

        Assertions.assertEquals("Owner", chatDto.getUserInfo().getRole());

        List<History> historyList = chatDto.getHistoryList();
        Assertions.assertNotNull(historyList);
//		Assertions.assertEquals(chatDto.getChatId(), historyList.get(0).getChatId());

        List<String> messages = chatDto.getHistoryList().stream()
                .map(History::getMessage)
                .collect(Collectors.toList());
        Assertions.assertTrue(messages.contains(OWNER_MESSAGE));
    }
}
