package com.company.app.telegram.integration.in.button.service;

import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;


@Service
@RequiredArgsConstructor
public class SimpleSendMessageCreator {

    public SendMessage create(String chatName, String buttonText, String callbackData, String text) {
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton(buttonText);
        inlineKeyboardButton.setCallbackData(callbackData);

        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        rowsInLine.add(List.of(inlineKeyboardButton));

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        markupInline.setKeyboard(rowsInLine);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatName);
        sendMessage.setText(text);
        sendMessage.setReplyMarkup(markupInline);
        return sendMessage;
    }

}