package com.company.app.telegram.component.impl;

import com.company.app.telegram.component.api.BinderExecutor;
import com.company.app.telegram.component.api.ChatActivationService;
import com.company.app.telegram.component.api.IncomingMessageHandler;
import com.company.app.telegram.component.api.TelegramBotConfig;
import com.company.app.telegram.component.data.ButtonFactory;
import com.company.app.telegram.domain.entity.Chat;
import com.company.app.telegram.domain.entity.History;
import com.company.app.telegram.domain.service.api.ChatService;
import com.company.app.telegram.domain.service.api.HistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Date;

@Slf4j
@Component
public class IncomingMessageHandlerImpl implements IncomingMessageHandler {

	@Autowired
	TelegramBotConfig telegramBotConfig;
	@Autowired
	HistoryService historyService;
	@Autowired
	ChatService chatService;
	@Autowired
	BinderExecutor binderExecutor;
	@Autowired
	ChatActivationService chatActivationService;

	@Override
	public void process(Update update) {
		if (isIncomingMessage(update)) {
			prepareToWork(update);
			showCommands(update);
		} else if (isCallback(update)) {
			handle(update);
		}
	}

	private static boolean isCallback(Update update) {
		return update.hasCallbackQuery();
	}

	private static boolean isIncomingMessage(Update update) {
		return update.getMessage() != null;
	}

	private void prepareToWork(Update update) {
		Message message = update.getMessage();
		Long chatId = message.getChatId();
		Chat chat = chatService.getChatOrCreateIfNotExist(chatId);
		saveHistory(chat, message.getText());
		chatActivationService.activate(chat);
	}

	private void showCommands(Update update) {
		SendMessage sendMessage = new SendMessage();
		sendMessage.setChatId(update.getMessage().getChatId());
		sendMessage.setText("Доступны следующие команды:");
		sendMessage.setReplyMarkup(ButtonFactory.inlineMarkup());
		telegramBotConfig.write(sendMessage);
	}

	private void handle(Update update) {
		CallbackQuery callbackQuery = update.getCallbackQuery();
		Long chatId = callbackQuery.getMessage().getChatId();
		String text = callbackQuery.getData();
		Chat chat = chatService.getChatOrCreateIfNotExist(chatId);

		saveHistory(chat, text);
		binderExecutor.execute(chat, text);
	}

	private void saveHistory(Chat chat, String text) {
		log.debug("Читаю из чата [{}] сообщение [{}].", chat.getChatId(), text);
		History history = History.builder()
				.chat(chat)
				.message(text)
				.source(chat.getChatId().toString())
				.target(telegramBotConfig.getName())
				.date(new Date())
				.build();
		historyService.save(history);
	}
}
