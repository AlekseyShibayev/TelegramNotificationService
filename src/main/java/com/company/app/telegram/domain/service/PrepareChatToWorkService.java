package com.company.app.telegram.domain.service;

import com.company.app.telegram.domain.entity.Chat;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

/**
 * Сервис, подготавливающий чат к работе.
 */
@Service
@RequiredArgsConstructor
public class PrepareChatToWorkService {

    private final HistoryService historyService;
    private final ChatService chatService;
    private final ChatActivationService chatActivationService;

    public Chat getPreparedToWorkChat(Message message, Long chatId) {
        Chat chat = chatService.getChatOrCreateIfNotExist(chatId.toString());
        historyService.saveHistory(chat, message.getText());
        chatActivationService.activate(chat);
        return chat;
    }

}
