package com.company.app.telegram.integration.in.button.button_callback_actions.all;

import com.company.app.telegram.domain.entity.Chat;
import com.company.app.telegram.integration.in.button.model.ButtonCallbackAction;
import com.company.app.telegram.integration.in.button.model.ButtonCallbackActionContext;
import com.company.app.telegram.integration.in.service.ChatActivationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class TelegramOffButtonCallbackAction implements ButtonCallbackAction {

    private static final String TYPE = "TG_OFF";

    private final ChatActivationService chatActivationService;

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public void doAction(ButtonCallbackActionContext context) {
        Chat chat = context.getChat();
        chatActivationService.deactivate(chat);
    }

}
