package com.company.app.telegram.incoming_message_handler.button.button_callback_actions;

import com.company.app.telegram.TelegramFacade;
import com.company.app.telegram.domain.entity.Chat;
import com.company.app.telegram.incoming_message_handler.button.model.ButtonCallbackAction;
import com.company.app.telegram.incoming_message_handler.button.model.ButtonCallbackActionContext;
import com.company.app.telegram.incoming_message_handler.button.service.ButtonFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;


@Slf4j
@Service
@RequiredArgsConstructor
public class WildberriesMainMenuButtonCallbackAction implements ButtonCallbackAction {

    private static final String TYPE = "WB_MAIN_MENU";

    private final TelegramFacade telegramFacade;
    private final ButtonFactory buttonFactory;

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public void doAction(ButtonCallbackActionContext context) {
        Chat chat = context.getChat();

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chat.getChatName());
        sendMessage.setText("Управление wildberries:");
        sendMessage.setReplyMarkup(buttonFactory.wildberriesMenuButtons());
        telegramFacade.writeToTargetChat(sendMessage);
    }

}
