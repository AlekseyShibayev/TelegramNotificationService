package com.company.app.telegram.incoming_message_handler.button.button_callback_actions.all;

import java.util.ArrayList;
import java.util.List;

import com.company.app.telegram.TelegramFacade;
import com.company.app.telegram.domain.entity.Chat;
import com.company.app.telegram.domain.enums.ModeType;
import com.company.app.telegram.domain.model.UpdateChat;
import com.company.app.telegram.domain.service.ChatService;
import com.company.app.telegram.incoming_message_handler.button.model.ButtonCallbackAction;
import com.company.app.telegram.incoming_message_handler.button.model.ButtonCallbackActionContext;
import com.company.app.telegram.incoming_message_handler.button.task_executor.IncomingMessageTaskExecutor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;


@Slf4j
@Service
@RequiredArgsConstructor
public class WildberriesDesireLotAddButtonCallbackAction implements ButtonCallbackAction {

    private static final String TYPE = "WB_DL_ADD";

    private final TelegramFacade telegramFacade;
    private final ChatService chatService;
    private final IncomingMessageTaskExecutor incomingMessageTaskExecutor;

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
                .setChatName(chat.getChatName())
                .setMode(ModeType.ADD_DESIRE.name()));

            showButtons(chat);
        } else {
            chatService.updateChat(new UpdateChat()
                .setChatName(chat.getChatName())
                .setMode(ModeType.DEFAULT.name()));

            incomingMessageTaskExecutor.processIncomingMessageTask(chat.getChatName(), ModeType.ADD_DESIRE.name());
        }
    }

    private void showButtons(Chat chat) {
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton("Я всё");
        inlineKeyboardButton.setCallbackData(TYPE + ButtonCallbackAction.BINDER_DELIMITER + "add");

        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        rowsInLine.add(List.of(inlineKeyboardButton));

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        markupInline.setKeyboard(rowsInLine);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chat.getChatName());
        sendMessage.setText("Добавляйте желания, парами сообщений, сперва ссылку на товар, потом цену. По окончании нажмите кнопку:");
        sendMessage.setReplyMarkup(markupInline);
        telegramFacade.writeToTargetChat(sendMessage);
    }

}