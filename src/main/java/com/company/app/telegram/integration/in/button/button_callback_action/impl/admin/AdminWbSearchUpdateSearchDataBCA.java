package com.company.app.telegram.integration.in.button.button_callback_action.impl.admin;

import com.company.app.telegram.TelegramFacade;
import com.company.app.telegram.domain.entity.Chat;
import com.company.app.telegram.domain.enums.ModeType;
import com.company.app.telegram.domain.model.UpdateChat;
import com.company.app.telegram.domain.service.ChatService;
import com.company.app.telegram.integration.in.button.button_callback_action.ButtonCallbackAction;
import com.company.app.telegram.integration.in.button.button_callback_action.model.ButtonCallbackActionContext;
import com.company.app.telegram.integration.in.button.button_callback_action.model.MessageSplitter;
import com.company.app.telegram.integration.in.button.service.SimpleSendMessageCreator;
import com.company.app.telegram.integration.in.button.task_executor.IncomingMessageTaskExecutor;
import com.company.app.wildberries.search.domain.dto.SearchDataDto;
import com.company.app.wildberries.search.domain.repository.SearchDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;


@Service
@RequiredArgsConstructor
public class AdminWbSearchUpdateSearchDataBCA implements ButtonCallbackAction {

    private static final String TYPE = "ADMIN_WB_SEARCH_UPDATE_SEARCH_DATA";

    private final SearchDataRepository searchDataRepository;
    private final TelegramFacade telegramFacade;
    private final IncomingMessageTaskExecutor incomingMessageTaskExecutor;
    private final ChatService chatService;
    private final SimpleSendMessageCreator simpleSendMessageCreator;

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
                .setChatId(chat.getId())
                .setModeType(ModeType.DEFAULT));

        incomingMessageTaskExecutor.processIncomingMessageTask(chat.getChatName(), ModeType.UPDATE_SEARCH_DATA);
    }

    private void doForUpdate(Chat chat) {
        chatService.updateChat(new UpdateChat()
                .setChatId(chat.getId())
                .setModeType(ModeType.UPDATE_SEARCH_DATA));

        String callbackData = TYPE + ButtonCallbackAction.BINDER_DELIMITER + "greedIndex";
        String text = "Введите индекс жадности и нажмите кнопку:";
        SendMessage sendMessage = simpleSendMessageCreator.createButtonWithCallback(chat.getChatName(), "Ok", callbackData, text);
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
        String callbackData = TYPE + ButtonCallbackAction.BINDER_DELIMITER + "update";
        String text ="Выберите действие:";
        SendMessage sendMessage = simpleSendMessageCreator.createButtonWithCallback(chat.getChatName(), "Изменить", callbackData, text);
        telegramFacade.writeToTargetChat(sendMessage);
    }

}