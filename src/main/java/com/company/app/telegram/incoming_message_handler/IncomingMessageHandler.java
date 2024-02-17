package com.company.app.telegram.incoming_message_handler;

import com.company.app.telegram.TelegramFacade;
import com.company.app.telegram.domain.entity.Chat;
import com.company.app.telegram.domain.entity.IncomingMessageTask;
import com.company.app.telegram.domain.enums.ModeType;
import com.company.app.telegram.domain.repository.IncomingMessageTaskRepository;
import com.company.app.telegram.domain.service.ChatService;
import com.company.app.telegram.domain.service.HistoryService;
import com.company.app.telegram.incoming_message_handler.button.ButtonCallbackActionExecutor;
import com.company.app.telegram.incoming_message_handler.button.service.ButtonFactory;
import com.company.app.telegram.incoming_message_handler.service.ChatActivationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Responsibility:
 * Process incoming messages, which send to telegram bot by user.
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class IncomingMessageHandler {

    private final TelegramFacade telegramFacade;
    private final HistoryService historyService;
    private final ChatService chatService;
    private final ButtonCallbackActionExecutor binderExecutor;
    private final ChatActivationService chatActivationService;
    private final IncomingMessageTaskRepository incomingMessageTaskRepository;
    private final ButtonFactory buttonFactory;

    @Transactional
    public void take(Update update) {
        if (isIncomingMessage(update)) {
            Message message = update.getMessage();
            Long chatId = message.getChatId();

            Chat chat = chatService.findChatByChatNameOrCreateIfNotExist(chatId.toString());
            historyService.saveHistory(chat, message.getText());
            chatActivationService.activate(chat);

            if (ModeType.DEFAULT.typeOf(chat)) {
                showMainMenuButtons(update);
            } else {
                IncomingMessageTask task = new IncomingMessageTask()
                        .setChatName(chat.getChatName())
                        .setModeType(chat.getMode().getType())
                        .setMessage(message.getText());
                incomingMessageTaskRepository.save(task);
            }

        } else if (isCallback(update)) {
            processCallback(update);
        }
    }

    private boolean isCallback(Update update) {
        return update.hasCallbackQuery();
    }

    private boolean isIncomingMessage(Update update) {
        return update.getMessage() != null;
    }

    private void showMainMenuButtons(Update update) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId());
        sendMessage.setText("Доступны следующие команды:");
        sendMessage.setReplyMarkup(buttonFactory.mainMenuButtons(update));
        telegramFacade.writeToTargetChat(sendMessage);
    }

    private void processCallback(Update update) {
        CallbackQuery callbackQuery = update.getCallbackQuery();
        Long chatId = callbackQuery.getMessage().getChatId();
        String text = callbackQuery.getData();
        Chat chat = chatService.findChatByChatNameOrCreateIfNotExist(chatId.toString());
        historyService.saveHistory(chat, text);
        binderExecutor.execute(chat, text);
    }

}