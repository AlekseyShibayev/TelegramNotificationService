package com.company.app.telegram.controller;

import com.company.app.telegram.component.TelegramOwnerService;
import com.company.app.telegram.domain.entity.Chat;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/telegram/owner")
public class TelegramOwnerController {

    private final TelegramOwnerService telegramOwnerService;

    /**
     * example: http://localhost:8080/telegram/owner/getAllTelegramSettingsAsJson
     */
    @GetMapping(value = "/getAllTelegramChats", produces = "application/json")
    public ResponseEntity<List<Chat>> getAllTelegramChats() {
        return ResponseEntity.ok(telegramOwnerService.findAll());
    }

}
