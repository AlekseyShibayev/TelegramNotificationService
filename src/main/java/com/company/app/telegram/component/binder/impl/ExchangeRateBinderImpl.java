package com.company.app.telegram.component.binder.impl;

import com.company.app.exchangerate.entity.ExchangeRate;
import com.company.app.exchangerate.service.ExchangeRateService;
import com.company.app.telegram.component.binder.BinderContainer;
import com.company.app.telegram.component.binder.api.ExchangeRateBinder;
import com.company.app.telegram.controller.TelegramController;
import com.company.app.telegram.dto.TargetMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExchangeRateBinderImpl implements ExchangeRateBinder {

	private static final String TYPE = "EX";

	@Autowired
	ExchangeRateService exchangeRateService;
	@Autowired
	TelegramController telegramController;

	@Override
	public String getType() {
		return TYPE;
	}

	@Override
	public void bind(BinderContainer binderContainer) {
		ExchangeRate last = exchangeRateService.getLast();

		TargetMessage targetMessage = TargetMessage.builder()
				.chatId(binderContainer.getChat().getChatId())
				.message(last.getAliexpressExchangeRate())
				.build();

		telegramController.say(targetMessage);
	}
}
