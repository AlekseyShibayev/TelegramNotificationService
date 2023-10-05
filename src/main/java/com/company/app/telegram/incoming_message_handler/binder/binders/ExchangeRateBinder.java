package com.company.app.telegram.incoming_message_handler.binder.binders;

import com.company.app.exchange_rate.controller.ExchangeRateController;
import com.company.app.exchange_rate.domain.entity.ExchangeRate;
import com.company.app.telegram.TelegramFacade;
import com.company.app.telegram.incoming_message_handler.binder.binder_strategy.Binder;
import com.company.app.telegram.incoming_message_handler.binder.binder_strategy.BinderContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExchangeRateBinder implements Binder {

    private static final String TYPE = "EX";

    private final ExchangeRateController exchangeRateController;
    private final TelegramFacade telegramFacade;

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public void bind(BinderContext binderContext) {
        ExchangeRate last = exchangeRateController.getLast().getBody();
        telegramFacade.writeToTargetChat(binderContext.getChat().getChatName(), last.getAliexpressExchangeRate());
    }

}
