package com.company.app.telegram.component;

import com.company.app.telegram.TelegramFacade;
import com.company.app.telegram.component.data.ButtonFactory;
import com.company.app.telegram.domain.entity.Chat;
import com.company.app.telegram.domain.service.ChatService;
import com.company.app.telegram.domain.service.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Обрабатывает сообщения, отправленные пользователем в телеграм бота.
 */
@Service
@RequiredArgsConstructor
public class IncomingMessageHandler {

    private final TelegramFacade telegramFacade;
    private final HistoryService historyService;
    private final ChatService chatService;
    private final BinderExecutor binderExecutor;
    private final PrepareChatToWorkService prepareChatToWorkService;

    public void process(Update update) {
        if (isIncomingMessage(update)) {
            prepareChatToWork(update);
            showCommands(update);
        } else if (isCallback(update)) {
            handle(update);
        }
    }

    private boolean isCallback(Update update) {
        return update.hasCallbackQuery();
    }

    private boolean isIncomingMessage(Update update) {
        return update.getMessage() != null;
    }

    private void prepareChatToWork(Update update) {
        Message message = update.getMessage();
        Long chatId = message.getChatId();
        prepareChatToWorkService.getPreparedToWorkChat(message, chatId);
    }

    private void showCommands(Update update) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId());
        sendMessage.setText("Доступны следующие команды:");
        sendMessage.setReplyMarkup(ButtonFactory.inlineMarkup());
        telegramFacade.writeToTargetChat(sendMessage);
    }

    private void handle(Update update) {
        CallbackQuery callbackQuery = update.getCallbackQuery();
        Long chatId = callbackQuery.getMessage().getChatId();
        String text = callbackQuery.getData();
        Chat chat = chatService.getChatOrCreateIfNotExist(chatId.toString());
        historyService.saveHistory(chat, text);
        binderExecutor.execute(chat, text);
    }

}