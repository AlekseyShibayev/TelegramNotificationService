package com.company.app.telegram.integration.in.button.button_callback_actions.admin;

import com.company.app.telegram.TelegramFacade;
import com.company.app.telegram.domain.entity.Chat;
import com.company.app.telegram.domain.enums.ModeType;
import com.company.app.telegram.domain.model.UpdateChat;
import com.company.app.telegram.domain.service.ChatService;
import com.company.app.telegram.integration.in.button.model.ButtonCallbackAction;
import com.company.app.telegram.integration.in.button.model.ButtonCallbackActionContext;
import com.company.app.telegram.integration.in.button.model.MessageSplitter;
import com.company.app.telegram.integration.in.button.task_executor.IncomingMessageTaskExecutor;
import com.company.app.wildberries.search.domain.dto.SearchDataDto;
import com.company.app.wildberries.search.domain.repository.SearchDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class AdminWbSearchUpdateSearchDataBCA implements ButtonCallbackAction {

    private static final String TYPE = "ADMIN_WB_SEARCH_UPDATE_SEARCH_DATA";

    private final SearchDataRepository searchDataRepository;
    private final TelegramFacade telegramFacade;
    private final IncomingMessageTaskExecutor incomingMessageTaskExecutor;
    private final ChatService chatService;

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public void doAction(ButtonCallbackActionContext context) {
        Chat chat = context.getChat();
        String message = context.getMessage();

        if (isFirstTimeHere(message)) {
            showCurrentSearchData(chat);
        } else {
            MessageSplitter splitter = MessageSplitter.of(message);

            if (splitter.isRightPartEqual("update")) {
                doForUpdate(chat);
            } else if (splitter.isRightPartEqual("greedIndex")) {
                doForGreedIndex(chat);
            }

        }
    }

    private void doForGreedIndex(Chat chat) {
        chatService.updateChat(new UpdateChat()
                .setChatName(chat.getChatName())
                .setMode(ModeType.DEFAULT.name()));

        incomingMessageTaskExecutor.processIncomingMessageTask(chat.getChatName(), ModeType.UPDATE_SEARCH_DATA);
    }

    private void doForUpdate(Chat chat) {
        chatService.updateChat(new UpdateChat()
                .setChatName(chat.getChatName())
                .setMode(ModeType.UPDATE_SEARCH_DATA.name()));

        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton("Ок");
        inlineKeyboardButton.setCallbackData(TYPE + ButtonCallbackAction.BINDER_DELIMITER + "greedIndex");

        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        rowsInLine.add(List.of(inlineKeyboardButton));

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        markupInline.setKeyboard(rowsInLine);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chat.getChatName());
        sendMessage.setText("Введите индекс жадности и нажмите кнопку:");
        sendMessage.setReplyMarkup(markupInline);
        telegramFacade.writeToTargetChat(sendMessage);
    }

    private void showCurrentSearchData(Chat chat) {
        SearchDataDto dto = searchDataRepository.findByChatName(chat.getChatName())
                .map(SearchDataDto::of)
                .orElse(SearchDataDto.empty());

        telegramFacade.writeToTargetChat(chat.getChatName(), dto);
        showButtons(chat);
    }

    private void showButtons(Chat chat) {
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton("Изменить");
        inlineKeyboardButton.setCallbackData(TYPE + ButtonCallbackAction.BINDER_DELIMITER + "update");

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