package com.company.app.telegram.config;

import com.company.app.common.outbox.domain.enums.Target;
import com.company.app.common.outbox.service.OutboxService;
import com.company.app.telegram.domain.service.HistoryService;
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
    private final OutboxService outboxService;

    public void sendToTargetChat(String chatName, Object message) {
        SendMessage sendMessage = SendMessage.builder().chatId(chatName).text(message.toString()).build();
        sendOneMessage(sendMessage);
    }

    public void sendToTargetChat(SendMessage sendMessage) {
        sendOneMessage(sendMessage);
    }

    private void sendOneMessage(SendMessage sendMessage) {
        historyService.saveHistory(sendMessage);
        outboxService.create(sendMessage.getChatId(), sendMessage.getText(), Target.TELEGRAM);
    }

}