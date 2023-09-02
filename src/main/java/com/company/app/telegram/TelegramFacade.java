package com.company.app.telegram;

import com.company.app.telegram.component.TelegramDistributionHub;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Component
@RequiredArgsConstructor
public class TelegramFacade {

    private final TelegramDistributionHub telegramDistributionHub;

    public void writeToEveryone(Object message) {
        telegramDistributionHub.writeToEveryone(message);
    }

    public void writeToTargetChat(String chatName, Object message) {
        telegramDistributionHub.writeToTargetChat(chatName, message);
    }

    public void writeToTargetChat(SendMessage sendMessage) {
        telegramDistributionHub.writeToTargetChat(sendMessage);
    }

}
