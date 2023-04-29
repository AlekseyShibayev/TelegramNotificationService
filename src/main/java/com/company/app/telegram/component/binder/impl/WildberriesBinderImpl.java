package com.company.app.telegram.component.binder.impl;

import com.company.app.core.tool.api.DataExtractorTool;
import com.company.app.telegram.component.api.TelegramBotConfig;
import com.company.app.telegram.component.binder.BinderContainer;
import com.company.app.telegram.component.binder.api.WildberriesBinder;
import com.google.common.collect.Lists;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.polls.SendPoll;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ForceReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButtonPollType;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

/**
 * https://api.telegram.org/bot<token>/METHOD_NAME
 * https://api.telegram.org/bot1585428394:AAH2XP_r0-IUQr3nPKqp-KH6I2z2W_rEC1s/getMe
 *
 * https://api.telegram.org/bot1585428394:AAH2XP_r0-IUQr3nPKqp-KH6I2z2W_rEC1s/getUpdates
 */
@Slf4j
@Component
public class WildberriesBinderImpl implements WildberriesBinder {

	private static final String TYPE = "WB";

	@Autowired
	DataExtractorTool dataExtractorTool;
	@Autowired
	TelegramBotConfig telegramBotConfig;

	@Override
	public String getType() {
		return TYPE;
	}

	@SneakyThrows
	@Override
	public void bind(BinderContainer binderContainer) {
//		ReplyKeyboardMarkup replyKeyboardMarkup = getReplyKeyboardMarkup();
//		SendMessage sendMessage = SendMessage.builder()
//				.chatId(binderContainer.getChat().getChatId())
//				.text("test_text")
//				.replyMarkup(replyKeyboardMarkup)
//				.build();

		telegramBotConfig.write(getSendPoll(binderContainer));
	}

	private ReplyKeyboardMarkup getReplyKeyboardMarkup() {
//		InlineKeyboardButton test = new InlineKeyboardButton("test");
//		test.setSwitchInlineQuery("test");

		KeyboardButton keyboardButton = new KeyboardButton();
		keyboardButton.setText("test1");

		KeyboardButton keyboardButton2 = new KeyboardButton();
		keyboardButton2.setText("test2");

		KeyboardButton keyboardButton3 = new KeyboardButton();
		keyboardButton3.setText("test3");

		KeyboardRow keyboardRow = new KeyboardRow();
		keyboardRow.add(keyboardButton);
		keyboardRow.add(keyboardButton2);
		keyboardRow.add(keyboardButton3);

		List<KeyboardRow> keyboardRowList = new ArrayList<>();
		keyboardRowList.add(keyboardRow);

		return ReplyKeyboardMarkup.builder()
				.keyboard(keyboardRowList)
				.inputFieldPlaceholder("???")
				.build();
	}

	private SendPoll getSendPoll(BinderContainer binderContainer) {
		InlineKeyboardMarkup inlineKeyboardMarkup = getInlineKeyboardMarkup();

		return SendPoll.builder()
				.chatId(binderContainer.getChat().getChatId())
				.question("Вопрос такой-то?")
				.options(Lists.newArrayList("Первый ответ", "Второй ответ", "Третий ответ"))
				.type("regular")
//				.correctOptionId(1)
//				.allowMultipleAnswers(true)
//				.isClosed(true)
				.replyMarkup(inlineKeyboardMarkup)
				.build();
	}

	private InlineKeyboardMarkup getInlineKeyboardMarkup() {
		List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
		rowsInLine.add(List.of(InlineKeyboardButton.builder().callbackData("123").text("321").build()));

		return InlineKeyboardMarkup.builder()
				.keyboard(rowsInLine)
				.build();
	}

	private ForceReplyKeyboard getForceReplyKeyboard() {
		return ForceReplyKeyboard.builder()
			.forceReply(false)
			.selective(false)
			.inputFieldPlaceholder("???")
			.build();
	}
}