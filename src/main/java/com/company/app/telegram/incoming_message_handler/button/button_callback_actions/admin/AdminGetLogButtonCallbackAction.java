package com.company.app.telegram.incoming_message_handler.button.button_callback_actions.admin;

import java.io.InputStream;

import com.company.app.common.tool.LogService;
import com.company.app.telegram.config.TelegramBotApi;
import com.company.app.telegram.domain.entity.Chat;
import com.company.app.telegram.incoming_message_handler.button.model.ButtonCallbackAction;
import com.company.app.telegram.incoming_message_handler.button.model.ButtonCallbackActionContext;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.objects.InputFile;


@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class AdminGetLogButtonCallbackAction implements ButtonCallbackAction {

    private static final String TYPE = "ADMIN_GET_LOG";

    private final LogService logService;
    private final TelegramBotApi telegramBotApi;

    @Override
    public String getType() {
        return TYPE;
    }

    @SneakyThrows
    @Override
    public void doAction(ButtonCallbackActionContext context) {
        Chat chat = context.getChat();

        try (InputStream targetStream = logService.getLogsAsInputStream()) {
            InputFile inputFile = new InputFile(targetStream, "logs.zip");

            SendDocument sendDocument = new SendDocument();
            sendDocument.setChatId(chat.getChatName());
            sendDocument.setDocument(inputFile);

            telegramBotApi.executeAsync(sendDocument);
        }
    }

}