package com.company.app.telegram.component;

import com.company.app.telegram.component.config.TelegramBotConfig;
import com.company.app.telegram.domain.entity.Chat;
import com.company.app.telegram.domain.service.api.ChatService;
import com.company.app.telegram.domain.service.api.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

/**
 * Обрабатывает сообщения, отправленные телеграм ботом пользователю.
 */
@Service
@RequiredArgsConstructor
public class OutgoingMessageHandler {

    private final HistoryService historyService;
    private final TelegramBotConfig telegramBotConfig;
    private final ChatService chatService;

    public void sendToEveryone(Object message) {
        chatService.getAll().stream()
                .filter(Chat::isEnableNotifications)
                .map(chat -> SendMessage.builder().text(message.toString()).chatId(chat.getChatName()).build())
                .forEach(this::sendOneMessage);
    }

    public void sendToTargetChat(String chatName, Object message) {
        SendMessage sendMessage = SendMessage.builder().chatId(chatName).text(message.toString()).build();
        sendOneMessage(sendMessage);
    }

    private void sendOneMessage(SendMessage sendMessage) {
        historyService.saveHistory(sendMessage);
        telegramBotConfig.write(sendMessage);
    }

}

