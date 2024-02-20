package com.company.app.telegram.incoming_message_handler.button.button_callback_actions.admin;

import com.company.app.telegram.TelegramFacade;
import com.company.app.telegram.domain.entity.Chat;
import com.company.app.telegram.incoming_message_handler.button.model.ButtonCallbackAction;
import com.company.app.telegram.incoming_message_handler.button.model.ButtonCallbackActionContext;
import com.company.app.wildberries.desire.service.WildberriesDesireLotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminWildberriesDesireLotManualRefreshButtonCallbackAction implements ButtonCallbackAction {

    private static final String TYPE = "ADMIN_WB_DL_MANUAL_REFRESH";

    private final TelegramFacade telegramFacade;
    private final WildberriesDesireLotService wildberriesDesireLotService;

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public void doAction(ButtonCallbackActionContext context) {
        Chat chat = context.getChat();

        wildberriesDesireLotService.search();
        telegramFacade.writeToTargetChat(chat.getChatName(), "success");
    }

}
