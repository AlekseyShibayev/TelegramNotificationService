package com.company.app.telegram.domain.service.impl;

import com.company.app.telegram.component.api.TelegramBotConfig;
import com.company.app.telegram.domain.entity.Chat;
import com.company.app.telegram.domain.service.api.ChatActivationService;
import com.company.app.telegram.domain.service.api.ChatService;
import com.company.app.telegram.domain.service.api.HistoryService;
import com.company.app.telegram.domain.service.api.PrepareChatToWorkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.Message;

@Slf4j
@Service
public class PrepareChatToWorkServiceImpl implements PrepareChatToWorkService {

	@Autowired
	private TelegramBotConfig telegramBotConfig;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private ChatService chatService;
	@Autowired
	private ChatActivationService chatActivationService;

	@Transactional
	@Override
	public void prepareToWork(Message message, Long chatId) {
		doDomainLogic(message, chatId);
	}

	private void doDomainLogic(Message message, Long chatId) {
		Chat chat = chatService.getChatOrCreateIfNotExist(chatId);
		historyService.saveHistory(chat, message.getText());
		chatActivationService.activate(chat);
	}
}
