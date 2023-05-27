package com.company.app.telegram.component.impl;

import com.company.app.telegram.component.api.IncomingMessageHandler;
import com.company.app.telegram.component.api.OutgoingMessageHandler;
import com.company.app.telegram.component.api.TelegramBotConfig;
import com.company.app.telegram.component.api.TelegramDistributionHub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class TelegramDistributionHubImpl implements TelegramDistributionHub {

	@Autowired
	private IncomingMessageHandler incomingMessageHandler;
	@Autowired
	private OutgoingMessageHandler outgoingMessageHandler;
	@Autowired
	private TelegramBotConfig telegramBotConfig;

	@Override
	public void read(Update update) {
		incomingMessageHandler.process(update);
	}

	@Override
	public void writeToEveryone(Object message) {
		outgoingMessageHandler.sendToEveryone(message);
	}

	@Override
	public void writeToTargetChat(String chatName, Object message) {
		outgoingMessageHandler.sendToTargetChat(chatName, message);
	}

	@Override
	public void writeToTargetChat(SendMessage sendMessage) {
		telegramBotConfig.write(sendMessage);
	}
}
