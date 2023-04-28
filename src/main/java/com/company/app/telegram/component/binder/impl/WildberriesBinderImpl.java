package com.company.app.telegram.component.binder.impl;

import com.company.app.core.tool.api.JsonSerializationTool;
import com.company.app.telegram.component.api.TelegramBotConfig;
import com.company.app.telegram.component.binder.BinderContainer;
import com.company.app.telegram.component.binder.api.WildberriesBinder;
import com.company.app.telegram.component.data.ButtonAndCommandRegistry;
import com.company.app.telegram.entity.Chat;
import com.company.app.wildberries.entity.Lot;
import com.company.app.wildberries.repository.LotRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.inlinequery.inputmessagecontent.InputTextMessageContent;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

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
		sendMessage.setText("Доступны следующие команды:");

		InlineKeyboardButton test = new InlineKeyboardButton("test");
		test.setSwitchInlineQuery("test");


		List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
		rowsInLine.add(List.of(test));


		InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
		markupInline.setKeyboard(rowsInLine);


		sendMessage.setReplyMarkup(markupInline);
		telegramBotConfig.write(sendMessage);
	}
}
