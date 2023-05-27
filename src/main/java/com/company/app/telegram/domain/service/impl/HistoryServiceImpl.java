package com.company.app.telegram.domain.service.impl;

import com.company.app.telegram.component.api.TelegramBotConfig;
import com.company.app.telegram.domain.entity.Chat;
import com.company.app.telegram.domain.entity.History;
import com.company.app.telegram.domain.repository.HistoryRepository;
import com.company.app.telegram.domain.service.api.ChatService;
import com.company.app.telegram.domain.service.api.HistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.Date;

@Slf4j
@Service
public class HistoryServiceImpl implements HistoryService {

	@Value("${telegram.isHistoryEnable}")
	private boolean isHistoryEnable;

	@Autowired
	private HistoryRepository historyRepository;
	@Autowired
	private ChatService chatService;
	@Autowired
	private TelegramBotConfig telegramBotConfig;

	@Transactional
	@Override
	public void saveHistory(Chat chat, String text) {
		log.debug("Читаю из чата [{}] сообщение [{}].", chat.getChatName(), text);
		if (isHistoryEnable) {
			History history = History.builder()
					.chat(chat)
					.message(text)
					.source(chat.getChatName())
					.target(telegramBotConfig.getName())
					.date(new Date())
					.build();
			historyRepository.save(history);
		}
	}

	@Transactional
	@Override
	public void saveHistory(SendMessage sendMessage) {
		log.debug("Пробую написать в телеграм [{}]: [{}].", sendMessage.getChatId(), sendMessage.getText());
		if (isHistoryEnable) {
			String chatId = sendMessage.getChatId();
			History history = History.builder()
					.chat(chatService.getChatOrCreateIfNotExist(chatId))
					.message(sendMessage.getText())
					.source(telegramBotConfig.getName())
					.target(chatId)
					.date(new Date())
					.build();
			historyRepository.save(history);
		}
	}
}
