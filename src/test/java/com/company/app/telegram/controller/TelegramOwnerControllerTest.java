package com.company.app.telegram.controller;

import com.company.app.core.SpringBootTestApplicationContext;
import com.company.app.telegram.domain.entity.Chat;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

class TelegramOwnerControllerTest extends SpringBootTestApplicationContext {

    @Autowired
    private TelegramOwnerController telegramOwnerController;

    @Test
    void telegram_owner_get_all_test() {
        List<Chat> chats = telegramOwnerController.getAllTelegramChats().getBody();
        Optional<Chat> first = chats.stream()
                .filter(chat -> "owner".equalsIgnoreCase(chat.getUserInfo().getRole()))
                .findFirst();
        Assertions.assertEquals("653606407", first.get().getChatName());
        Assertions.assertEquals("Aleksey", first.get().getUserInfo().getName());
    }

}
