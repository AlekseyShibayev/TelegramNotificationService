package com.company.app.telegram.component.impl;

import com.company.app.telegram.component.api.BinderExecutor;
import com.company.app.telegram.component.api.IncomingMessageHandler;
import com.company.app.telegram.component.api.TelegramBotConfig;
import com.company.app.telegram.component.data.ButtonFactory;
import com.company.app.telegram.domain.entity.Chat;
import com.company.app.telegram.domain.service.api.ChatService;
import com.company.app.telegram.domain.service.api.HistoryService;
import com.company.app.telegram.domain.service.api.PrepareChatToWorkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Component
public class IncomingMessageHandlerImpl implements IncomingMessageHandler {

	@Autowired
	private TelegramBotConfig telegramBotConfig;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private ChatService chatService;
	@Autowired
	private BinderExecutor binderExecutor;
	@Autowired
	private PrepareChatToWorkService prepareChatToWorkService;

	@Override
	public void process(Update update) {
		if (isIncomingMessage(update)) {
			prepareToWork(update);
			showCommands(update);
		} else if (isCallback(update)) {
			handle(update);
		}
	}

	private boolean isCallback(Update update) {
		return update.hasCallbackQuery();
	}

	private boolean isIncomingMessage(Update update) {
		return update.getMessage() != null;
	}

	private void prepareToWork(Update update) {
		Message message = update.getMessage();
		Long chatId = message.getChatId();
		prepareChatToWorkService.prepareToWork(message, chatId);
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
		Chat chat = chatService.getChatOrCreateIfNotExist(chatId.toString());
		historyService.saveHistory(chat, text);
		binderExecutor.execute(chat, text);
	}
}
