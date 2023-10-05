package com.company.app.telegram.incoming_message.binder.binders;

import com.company.app.telegram.TelegramFacade;
import com.company.app.telegram.domain.entity.Chat;
import com.company.app.telegram.domain.entity.Mode;
import com.company.app.telegram.domain.enums.ModeType;
import com.company.app.telegram.domain.repository.ChatRepository;
import com.company.app.telegram.domain.repository.ModeRepository;
import com.company.app.telegram.incoming_message.binder.binder_strategy.Binder;
import com.company.app.telegram.incoming_message.binder.binder_strategy.BinderContext;
import com.company.app.telegram.incoming_message.message_executor.IncomingMessageTaskExecutor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class WildberriesDesireLotAdderBinder implements Binder {

    private static final String TYPE = "WB_DL_ADD";

    private final TelegramFacade telegramFacade;
    private final ModeRepository modeRepository;
    private final ChatRepository chatRepository;
    private final IncomingMessageTaskExecutor incomingMessageTaskExecutor;

    @Override
    public String getType() {
        return TYPE;
    }

    @Transactional
    @Override
    public void bind(BinderContext binderContext) {
        execute(binderContext);
    }

    public void execute(BinderContext binderContext) {
        Chat chat = binderContext.getChat();
        String incomingMessage = binderContext.getMessage();

        if (isFirstTimeHere(incomingMessage)) {
            Mode newMode = modeRepository.findByType(ModeType.ADD_DESIRE);
            chat.setMode(newMode);
            chatRepository.save(chat);
            showButtons(chat);
        } else {
            Mode newMode = modeRepository.findByType(ModeType.DEFAULT);
            chat.setMode(newMode);
            chatRepository.save(chat);
            incomingMessageTaskExecutor.processIncomingMessageTask(chat, ModeType.ADD_DESIRE.getType());
        }
    }

    private void showButtons(Chat chat) {
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton("Я всё");
        inlineKeyboardButton.setCallbackData(TYPE + Binder.BINDER_DELIMITER + "1");

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
