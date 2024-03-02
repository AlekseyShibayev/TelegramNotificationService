package com.company.app.telegram.integration.in.button.button_callback_actions.admin;

import com.company.app.exchange_rate.domain.repository.ExchangeRepository;
import com.company.app.telegram.TelegramFacade;
import com.company.app.telegram.integration.in.button.model.ButtonCallbackAction;
import com.company.app.telegram.integration.in.button.model.ButtonCallbackActionContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AdminExchangeRateButtonCallbackAction implements ButtonCallbackAction {

    private static final String TYPE = "ADMIN_ER_BUTTON";

    private final ExchangeRepository exchangeRepository;
    private final TelegramFacade telegramFacade;

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public void doAction(ButtonCallbackActionContext context) {
        exchangeRepository.findFirstByOrderByCreationDateDesc()
            .ifPresentOrElse(exchangeRate -> telegramFacade.writeToTargetChat(context.getChat().getChatName(), exchangeRate.getValue())
                , () -> telegramFacade.writeToTargetChat(context.getChat().getChatName(), "Курса ещё нет"));
    }

}