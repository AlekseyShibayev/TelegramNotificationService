package com.company.app.telegram.integration.in.button.button_callback_actions.admin;

import com.company.app.telegram.TelegramFacade;
import com.company.app.telegram.domain.entity.Chat;
import com.company.app.telegram.domain.enums.ModeType;
import com.company.app.telegram.integration.in.button.model.ButtonCallbackAction;
import com.company.app.telegram.integration.in.button.model.ButtonCallbackActionContext;
import com.company.app.telegram.integration.in.button.task_executor.IncomingMessageTaskExecutor;
import com.company.app.wildberries.search.domain.dto.SearchDataDto;
import com.company.app.wildberries.search.domain.entity.SearchData;
import com.company.app.wildberries.search.domain.repository.SearchDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AdminWbSearchUpdateSearchDataBCA implements ButtonCallbackAction {

    private static final String TYPE = "ADMIN_WB_SEARCH_UPDATE_SEARCH_DATA";

    private final SearchDataRepository searchDataRepository;
    private final TelegramFacade telegramFacade;
    private final IncomingMessageTaskExecutor incomingMessageTaskExecutor;

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public void doAction(ButtonCallbackActionContext context) {
        Chat chat = context.getChat();
        String message = context.getMessage();

        if (isFirstTimeHere(message)) {
            SearchDataDto dto = searchDataRepository.findByChatName(chat.getChatName())
                    .map(SearchDataDto::of)
                    .orElse(SearchDataDto.empty());

            telegramFacade.writeToTargetChat(chat.getChatName(), dto);
            showButtons(chat);
        } else {
            incomingMessageTaskExecutor.processIncomingMessageTask(chat.getChatName(), ModeType.UPDATE_SEARCH_DATA);
        }
    }

    private void showButtons(Chat chat) {
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton("Изменить");
        inlineKeyboardButton.setCallbackData(TYPE + ButtonCallbackAction.BINDER_DELIMITER + "add");

        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        rowsInLine.add(List.of(inlineKeyboardButton));

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        markupInline.setKeyboard(rowsInLine);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chat.getChatName());
        sendMessage.setText("Выберите действие:");
        sendMessage.setReplyMarkup(markupInline);
        telegramFacade.writeToTargetChat(sendMessage);
    }

}