package com.company.app.telegram.integration.in.button.button_callback_action.impl.all;

import com.company.app.telegram.TelegramFacade;
import com.company.app.telegram.domain.entity.Chat;
import com.company.app.telegram.domain.enums.ModeType;
import com.company.app.telegram.domain.model.UpdateChat;
import com.company.app.telegram.domain.service.ChatService;
import com.company.app.telegram.integration.in.button.button_callback_action.ButtonCallbackAction;
import com.company.app.telegram.integration.in.button.button_callback_action.model.ButtonCallbackActionContext;
import com.company.app.telegram.integration.in.button.service.SimpleSendMessageCreator;
import com.company.app.telegram.integration.in.button.task_executor.IncomingMessageTaskExecutor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;


@Slf4j
@Service
@RequiredArgsConstructor
public class WildberriesDesireLotAddButtonCallbackAction implements ButtonCallbackAction {

    private static final String TYPE = "WB_DL_ADD";

    private final TelegramFacade telegramFacade;
    private final ChatService chatService;
    private final IncomingMessageTaskExecutor incomingMessageTaskExecutor;
    private final SimpleSendMessageCreator simpleSendMessageCreator;

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public void doAction(ButtonCallbackActionContext context) {
        Chat chat = context.getChat();
        String incomingMessage = context.getMessage();

        if (isFirstTimeHere(incomingMessage)) {
            chatService.updateChat(new UpdateChat()
                .setChatId(chat.getId())
                .setModeType(ModeType.ADD_DESIRE));

            showButtons(chat);
        } else {
            chatService.updateChat(new UpdateChat()
                .setChatId(chat.getId())
                .setModeType(ModeType.DEFAULT));

            incomingMessageTaskExecutor.processIncomingMessageTask(chat.getChatName(), ModeType.ADD_DESIRE);
        }
    }

    private void showButtons(Chat chat) {
        String callbackData = TYPE + ButtonCallbackAction.BINDER_DELIMITER + "add";
        String text = "Добавляйте желания, парами сообщений, сперва ссылку на товар, потом цену. По окончании нажмите кнопку:";
        SendMessage sendMessage = simpleSendMessageCreator.createButtonWithCallback(chat.getChatName(), "Я всё", callbackData, text);

        telegramFacade.writeToTargetChat(sendMessage);
    }

}