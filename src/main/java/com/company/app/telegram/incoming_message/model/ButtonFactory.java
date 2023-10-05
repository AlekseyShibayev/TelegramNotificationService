package com.company.app.telegram.incoming_message.model;

import lombok.experimental.UtilityClass;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class ButtonFactory {

    private static final InlineKeyboardButton TG_OFF_BUTTON = new InlineKeyboardButton("Отключить уведомления");
    private static final InlineKeyboardButton TG_ON_BUTTON = new InlineKeyboardButton("Включить уведомления");
    private static final InlineKeyboardButton WB_DL_SHOW = new InlineKeyboardButton("Покажи что нашёл");
    private static final InlineKeyboardButton WB_DL_ADD = new InlineKeyboardButton("Добавить желания");
    private static final InlineKeyboardButton WB_DL_REMOVE = new InlineKeyboardButton("Удалить желания");
//    private static final InlineKeyboardButton ER_BUTTON = new InlineKeyboardButton("Последний найденный курс (DEV)");
//    private static final InlineKeyboardButton WB_SEARCH_BUTTON = new InlineKeyboardButton("START_WB_SEARCH (DEV)");

    public static InlineKeyboardMarkup inlineMarkup() {
        TG_OFF_BUTTON.setCallbackData("TG_OFF");
        TG_ON_BUTTON.setCallbackData("TG_ON");
        WB_DL_SHOW.setCallbackData("WB_DL_SHOW");
        WB_DL_ADD.setCallbackData("WB_DL_ADD");
        WB_DL_REMOVE.setCallbackData("WB_DL_REMOVE");
//        ER_BUTTON.setCallbackData("EX");
//        WB_SEARCH_BUTTON.setCallbackData("WB_SEARCH");

        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        rowsInLine.add(List.of(TG_OFF_BUTTON));
        rowsInLine.add(List.of(TG_ON_BUTTON));
        rowsInLine.add(List.of(WB_DL_SHOW));
        rowsInLine.add(List.of(WB_DL_ADD));
        rowsInLine.add(List.of(WB_DL_REMOVE));
//        rowsInLine.add(List.of(ER_BUTTON));
//        rowsInLine.add(List.of(WB_SEARCH_BUTTON));

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        markupInline.setKeyboard(rowsInLine);

        return markupInline;
    }
}
