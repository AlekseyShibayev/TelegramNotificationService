package com.company.app.telegram.component.impl;

import com.company.app.telegram.component.api.OutgoingMessageHandler;
import com.company.app.telegram.component.api.TelegramBotConfig;
import com.company.app.telegram.domain.entity.Chat;
import com.company.app.telegram.domain.service.api.ChatService;
import com.company.app.telegram.domain.service.api.HistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Slf4j
@Component
public class OutgoingMessageHandlerImpl implements OutgoingMessageHandler {

	@Autowired
	private HistoryService historyService;
	@Autowired
	private TelegramBotConfig telegramBotConfig;
	@Autowired
	private ChatService chatService;

	@Override
	public void sendToEveryone(Object message) {
		chatService.getAll().stream()
				.filter(Chat::isEnableNotifications)
				.map(chat -> SendMessage.builder().text(message.toString()).chatId(chat.getChatName().toString()).build())
				.forEach(this::sendOneMessage);
	}

	@Override
	public void sendToTargetChat(String chatId, Object message) {
		SendMessage sendMessage = SendMessage.builder().chatId(chatId).text(message.toString()).build();
		sendOneMessage(sendMessage);
	}

	private void sendOneMessage(SendMessage sendMessage) {
		historyService.saveHistory(sendMessage);
		telegramBotConfig.write(sendMessage);
	}
}
