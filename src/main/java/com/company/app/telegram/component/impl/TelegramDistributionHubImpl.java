package com.company.app.telegram.component.impl;

import com.company.app.telegram.component.api.IncomingMessageHandler;
import com.company.app.telegram.component.api.OutgoingMessageHandler;
import com.company.app.telegram.component.api.TelegramDistributionHub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class TelegramDistributionHubImpl implements TelegramDistributionHub {

	@Autowired
	IncomingMessageHandler incomingMessageHandler;
	@Autowired
	OutgoingMessageHandler outgoingMessageHandler;

	@Override
	public void read(Update update) {
		incomingMessageHandler.process(update);
	}

	@Override
	public void writeToEveryone(Object message) {
		outgoingMessageHandler.sendToEveryone(message);
	}

	@Override
	public void writeToTargetChat(Long chatId, Object message) {
		outgoingMessageHandler.sendToTargetChat(chatId, message);
	}
}
