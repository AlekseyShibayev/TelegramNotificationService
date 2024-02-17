package com.company.app.telegram.incoming_message_handler.button.button_callback_actions;

import com.company.app.common.timer.domain.enums.ActionType;
import com.company.app.common.timer.service.TimerService;
import com.company.app.telegram.TelegramFacade;
import com.company.app.telegram.domain.entity.Chat;
import com.company.app.telegram.domain.entity.Mode;
import com.company.app.telegram.domain.enums.ModeType;
import com.company.app.telegram.domain.repository.ChatRepository;
import com.company.app.telegram.domain.repository.ModeRepository;
import com.company.app.telegram.incoming_message_handler.button.task_executor.IncomingMessageTaskExecutor;
import com.company.app.telegram.incoming_message_handler.button.model.ButtonCallbackAction;
import com.company.app.telegram.incoming_message_handler.button.model.ButtonCallbackActionContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class WildberriesDesireLotAddButtonCallbackAction implements ButtonCallbackAction {

    private static final String TYPE = "WB_DL_ADD";

    private final TelegramFacade telegramFacade;
    private final ModeRepository modeRepository;
    private final ChatRepository chatRepository;
    private final IncomingMessageTaskExecutor incomingMessageTaskExecutor;
    private final TimerService timerService;

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public void doAction(ButtonCallbackActionContext context) {
        Chat chat = context.getChat();
        String incomingMessage = context.getMessage();

        if (isFirstTimeHere(incomingMessage)) {
            timerService.start(chat.getChatName(), ActionType.ROLLBACK_CHAT_MODE_TO_DEFAULT);

            Mode newMode = modeRepository.findByType(ModeType.ADD_DESIRE);
            chat.setMode(newMode);
            chatRepository.save(chat);

            showButtons(chat);
        } else {
            timerService.stop(chat.getChatName(), ActionType.ROLLBACK_CHAT_MODE_TO_DEFAULT);

            Mode newMode = modeRepository.findByType(ModeType.DEFAULT);
            chat.setMode(newMode);
            chatRepository.save(chat);
            incomingMessageTaskExecutor.processIncomingMessageTask(chat.getChatName(), ModeType.ADD_DESIRE.getType());
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