package com.company.app.telegram.binder.impl;

import com.company.app.exchange_rate.controller.ExchangeRateController;
import com.company.app.exchange_rate.domain.entity.ExchangeRate;
import com.company.app.telegram.binder.BinderContainer;
import com.company.app.telegram.binder.api.ExchangeRateBinder;
import com.company.app.telegram.component.TelegramFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExchangeRateBinderImpl implements ExchangeRateBinder {

	private static final String TYPE = "EX";

	@Autowired
	ExchangeRateController exchangeRateController;
	@Autowired
	TelegramFacade telegramFacade;

	@Override
	public String getType() {
		return TYPE;
	}

	@Override
	public void bind(BinderContainer binderContainer) {
		ExchangeRate last = exchangeRateController.getLast().getBody();
		telegramFacade.writeToTargetChat(binderContainer.getChat().getChatId(), last.getAliexpressExchangeRate());
	}
}
