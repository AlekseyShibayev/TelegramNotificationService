package com.company.app.telegram.domain.service;

import java.util.Date;

import com.company.app.telegram.domain.entity.Chat;
import com.company.app.telegram.domain.entity.History;
import com.company.app.telegram.domain.repository.HistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;


@Slf4j
@Service
@RequiredArgsConstructor
public class HistoryService {

    @Value("${telegram.isHistoryEnable}")
    private boolean isHistoryEnable;

    private final HistoryRepository historyRepository;
    private final ChatService chatService;

    public void saveHistory(Chat chat, String text) {
        log.debug("try to read from telegram with chat name: [{}] message: [{}]", chat.getChatName(), text);
        if (isHistoryEnable) {
            History history = History.builder()
                .chat(chat)
                .message(text)
                .source(chat.getChatName())
                .target("telegram")
                .date(new Date())
                .build();
            historyRepository.save(history);
        }
    }

    public void saveHistory(SendMessage sendMessage) {
        log.debug("try to write to telegram with chat name: [{}] message: [{}]", sendMessage.getChatId(), sendMessage.getText());
        if (isHistoryEnable) {
            String chatId = sendMessage.getChatId();
            History history = History.builder()
                .chat(chatService.findChatByChatNameOrCreateIfNotExist(chatId))
                .message(sendMessage.getText())
                .source("telegram")
                .target(chatId)
                .date(new Date())
                .build();
            historyRepository.save(history);
        }
    }

}