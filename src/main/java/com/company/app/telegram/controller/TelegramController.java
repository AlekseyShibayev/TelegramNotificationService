package com.company.app.telegram.controller;

import com.company.app.telegram.TelegramFacade;
import com.company.app.telegram.domain.dto.TargetMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/telegram")
public class TelegramController {

    private final TelegramFacade telegramFacade;

    @PostMapping(value = "/say", produces = "application/json")
    public ResponseEntity<Boolean> say(@RequestBody TargetMessage targetMessage) {
        telegramFacade.writeToTargetChat(targetMessage.getChatName(), targetMessage.getMessage());
        return ResponseEntity.ok(true);
    }

}
