package com.company.app.telegram;

import com.company.app.telegram.component.config.OutgoingMessageHandler;
import com.company.app.telegram.component.config.TelegramBotConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Service
@RequiredArgsConstructor
public class TelegramFacade {

    private final OutgoingMessageHandler outgoingMessageHandler;
    private final TelegramBotConfig telegramBotConfig;

    public void writeToEveryone(Object message) {
        outgoingMessageHandler.sendToEveryone(message);
    }

    public void writeToTargetChat(String chatName, Object message) {
        outgoingMessageHandler.sendToTargetChat(chatName, message);
    }

    public void writeToTargetChat(SendMessage sendMessage) {
        telegramBotConfig.write(sendMessage);
    }

}
