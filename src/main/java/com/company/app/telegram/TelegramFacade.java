package com.company.app.telegram;

import com.company.app.telegram.config.OutgoingMessageHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;


@Service
@RequiredArgsConstructor
public class TelegramFacade {

    private final OutgoingMessageHandler outgoingMessageHandler;

    public void writeToTargetChat(String chatName, Object message) {
        outgoingMessageHandler.sendToTargetChat(chatName, message);
    }

    public void writeToTargetChat(SendMessage sendMessage) {
        outgoingMessageHandler.sendToTargetChat(sendMessage);
    }

}