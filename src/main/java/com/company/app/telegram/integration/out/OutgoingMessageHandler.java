package com.company.app.telegram.integration.out;

import com.company.app.common.outbox.OutboxService;
import com.company.app.common.outbox.domain.enums.Target;
import com.company.app.telegram.domain.service.HistoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
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

    @SneakyThrows
    private void sendOneMessage(SendMessage sendMessage) {
        historyService.saveHistory(sendMessage);
        outboxService.create(sendMessage.getChatId(), new ObjectMapper().writeValueAsString(sendMessage), Target.TELEGRAM);
    }

}