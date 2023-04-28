package com.company.app.telegram.component.binder.impl;

import com.company.app.core.tool.api.JsonSerializationTool;
import com.company.app.telegram.component.api.TelegramBotConfig;
import com.company.app.telegram.component.binder.BinderContainer;
import com.company.app.telegram.component.binder.api.WildberriesBinder;
import com.company.app.telegram.component.data.ButtonAndCommandRegistry;
import com.company.app.telegram.entity.Chat;
import com.company.app.wildberries.entity.Lot;
import com.company.app.wildberries.repository.LotRepository;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.games.CallbackGame;
import org.telegram.telegrambots.meta.api.objects.inlinequery.inputmessagecontent.InputTextMessageContent;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButtonPollType;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class WildberriesBinderImpl implements WildberriesBinder {

	private static final String TYPE = "WB";

	private static final InputTextMessageContent TEST_BUTTON = new InputTextMessageContent("test_button");

	@Autowired
	LotRepository lotRepository;
	@Autowired
	JsonSerializationTool<Lot> jsonSerializationTool;
	@Autowired
	TelegramBotConfig telegramBotConfig;

	@Override
	public String getType() {
		return TYPE;
	}

	@Override
	public void bind(BinderContainer binderContainer) {
		Chat chat = binderContainer.getChat();
		String message = binderContainer.getMessage();

		log.debug("[{}]", binderContainer);
		if (message.equals("WB")) {
			showCommands(chat.getChatId().toString());
		}


//		List<Lot> lots = jsonSerializationTool.load(string, Lot.class);


//
//		if (log.isDebugEnabled()) {
//			log.debug("Пробую добавить лотов [{}].", lots.size());
//		}
//
//		lotRepository.saveAll(lots);
	}

	private void showCommands(String chatId) {
		SendMessage sendMessage = new SendMessage();
		sendMessage.setChatId(chatId);
		sendMessage.setText("Отредактируй и отправь:");


		InlineKeyboardButton test = new InlineKeyboardButton("test");
		test.setSwitchInlineQuery("test");

		ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

		KeyboardRow keyboardRow = new KeyboardRow();
		keyboardRow.add(KeyboardButton.builder().text("Poll").requestPoll(new KeyboardButtonPollType("quiz")).build());

		KeyboardButton keyboardButton = new KeyboardButton();
		keyboardButton.setText("test");
		keyboardButton.setRequestPoll(new KeyboardButtonPollType());

		KeyboardButton keyboardButton2 = new KeyboardButton();
		keyboardButton2.setText("test2");
		keyboardButton2.setRequestPoll(new KeyboardButtonPollType());

		KeyboardButton keyboardButton3 = new KeyboardButton();
		keyboardButton3.setText("test3");
		keyboardButton3.setRequestPoll(new KeyboardButtonPollType());

		keyboardRow.add(keyboardButton);
		keyboardRow.add(keyboardButton2);
		keyboardRow.add(keyboardButton3);

		List<KeyboardRow> keyboardRowList = new ArrayList<>();
		keyboardRowList.add(keyboardRow);

		replyKeyboardMarkup.setKeyboard(keyboardRowList);

		sendMessage.setReplyMarkup(replyKeyboardMarkup);
		telegramBotConfig.write(sendMessage);

		sendMessage.setReplyMarkup(replyKeyboardMarkup);
		telegramBotConfig.write(sendMessage);
	}
}
//
//	private void showCommands(String chatId) {
//		SendMessage sendMessage = new SendMessage();
//		sendMessage.setChatId(chatId);
//		sendMessage.setText("Доступны следующие команды:");
//
//		InlineKeyboardButton test = new InlineKeyboardButton("test");
//		test.setSwitchInlineQuery("test");
//
//		ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
//
//		KeyboardRow keyboardRow = new KeyboardRow();
//		KeyboardButton keyboardButton = new KeyboardButton();
//		keyboardButton.setText("test");
//		keyboardButton.setRequestPoll(new KeyboardButtonPollType());
//
//		KeyboardButton keyboardButton2 = new KeyboardButton();
//		keyboardButton2.setText("test2");
//		keyboardButton2.setRequestPoll(new KeyboardButtonPollType());
//
//		KeyboardButton keyboardButton3 = new KeyboardButton();
//		keyboardButton3.setText("test3");
//		keyboardButton3.setRequestPoll(new KeyboardButtonPollType());
//
//		keyboardRow.add(keyboardButton);
//		keyboardRow.add(keyboardButton2);
//		keyboardRow.add(keyboardButton3);
//
//		List<KeyboardRow> keyboardRowList = new ArrayList<>();
//		keyboardRowList.add(keyboardRow);
//
//		replyKeyboardMarkup.setKeyboard(keyboardRowList);
//
//		sendMessage.setReplyMarkup(replyKeyboardMarkup);
//		telegramBotConfig.write(sendMessage);
//	}


//	ForceReplyKeyboard forceReplyKeyboard = new ForceReplyKeyboard();
//		forceReplyKeyboard.setForceReply(true);
//				forceReplyKeyboard.setInputFieldPlaceholder("WB 15694225,6000,17");