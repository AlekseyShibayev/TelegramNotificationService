package com.company.app.telegram.integration.in.button.button_callback_action.impl.admin;

import com.company.app.telegram.TelegramFacade;
import com.company.app.telegram.domain.entity.Chat;
import com.company.app.telegram.integration.in.button.button_callback_action.ButtonCallbackAction;
import com.company.app.telegram.integration.in.button.button_callback_action.model.ButtonCallbackActionContext;
import com.company.app.wildberries.desire.WildberriesDesireSearcher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class AdminWildberriesDesireLotManualRefreshButtonCallbackAction implements ButtonCallbackAction {

    private static final String TYPE = "ADMIN_WB_DL_MANUAL_REFRESH";

    private final TelegramFacade telegramFacade;
    private final WildberriesDesireSearcher wildberriesDesireSearcher;

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public void doAction(ButtonCallbackActionContext context) {
        Chat chat = context.getChat();

        wildberriesDesireSearcher.search();

        telegramFacade.writeToTargetChat(chat.getChatName(), "success");
    }

}
