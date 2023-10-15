package com.company.app.telegram.incoming_message_handler.button.button_callback_actions;

import com.company.app.telegram.TelegramFacade;
import com.company.app.telegram.domain.entity.Chat;
import com.company.app.telegram.incoming_message_handler.button.model.ButtonCallbackAction;
import com.company.app.telegram.incoming_message_handler.button.model.ButtonCallbackActionContext;
import com.company.app.telegram.incoming_message_handler.button.model.ButtonFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminMainMenuButtonCallbackAction implements ButtonCallbackAction {

    private static final String TYPE = "ADMIN_MAIN_MENU";

    private final TelegramFacade telegramFacade;

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public void doAction(ButtonCallbackActionContext context) {
        Chat chat = context.getChat();

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chat.getChatName());
        sendMessage.setText("Админка:");
        sendMessage.setReplyMarkup(ButtonFactory.adminMenuButtons());
        telegramFacade.writeToTargetChat(sendMessage);
    }

}
