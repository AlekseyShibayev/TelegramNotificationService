package com.company.app.telegram.incoming_message_handler.button.button_callback_actions;

import com.company.app.exchange_rate.domain.repository.ExchangeRepository;
import com.company.app.telegram.TelegramFacade;
import com.company.app.telegram.incoming_message_handler.button.model.ButtonCallbackAction;
import com.company.app.telegram.incoming_message_handler.button.model.ButtonCallbackActionContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ExchangeRateButtonCallbackAction implements ButtonCallbackAction {

    private static final String TYPE = "EX";

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
