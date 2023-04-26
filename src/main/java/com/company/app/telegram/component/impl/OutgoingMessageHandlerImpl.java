package com.company.app.telegram.component.impl;

import com.company.app.telegram.component.api.OutgoingMessageHandler;
import com.company.app.telegram.component.api.TelegramBotConfig;
import com.company.app.telegram.entity.Chat;
import com.company.app.telegram.entity.History;
import com.company.app.telegram.service.api.ChatService;
import com.company.app.telegram.service.api.HistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.Date;

@Slf4j
@Component
public class OutgoingMessageHandlerImpl implements OutgoingMessageHandler {

	@Autowired
	HistoryService historyService;
	@Autowired
	TelegramBotConfig telegramBotConfig;
	@Autowired
	ChatService chatService;

	@Override
	public void sendToEveryone(Object message) {
		chatService.getAll().stream()
				.filter(Chat::isEnableNotifications)
				.map(chat -> SendMessage.builder().text(message.toString()).chatId(chat.getChatId().toString()).build())
				.forEach(this::sendOneMessage);
	}

	@Override
	public void sendToTargetChat(Long chatId, Object message) {
		SendMessage sendMessage = SendMessage.builder().text(message.toString()).chatId(chatId).build();
		sendOneMessage(sendMessage);
	}

	private void sendOneMessage(SendMessage sendMessage) {
		saveHistory(sendMessage);
		telegramBotConfig.write(sendMessage);
	}

	private void saveHistory(SendMessage sendMessage) {
		log.debug("Пробую написать в телеграм [{}]: [{}].", sendMessage.getChatId(), sendMessage.getText());
		String chatId = sendMessage.getChatId();
		History history = History.builder()
				.chat(chatService.getChatOrCreateIfNotExist(Long.valueOf(chatId)))
				.message(sendMessage.getText())
				.source(telegramBotConfig.getName())
				.target(chatId)
				.date(new Date())
				.build();
		historyService.save(history);
	}
}
