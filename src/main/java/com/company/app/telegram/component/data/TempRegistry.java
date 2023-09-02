package com.company.app.telegram.component.data;

import com.company.app.telegram.component.binder.BinderContext;
import com.google.common.collect.Lists;
import org.telegram.telegrambots.meta.api.methods.polls.SendPoll;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ForceReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

/**
 * https://api.telegram.org/bot<token>/METHOD_NAME
 * https://api.telegram.org/bot1585428394:AAH2XP_r0-IUQr3nPKqp-KH6I2z2W_rEC1s/getMe
 * https://api.telegram.org/bot1585428394:AAH2XP_r0-IUQr3nPKqp-KH6I2z2W_rEC1s/getUpdates
 */
@Deprecated
public class TempRegistry {

    private ForceReplyKeyboard getForceReplyKeyboard() {
        return ForceReplyKeyboard.builder()
                .forceReply(true)
                .selective(true)
                .inputFieldPlaceholder("артикул;желаемая_цена;дисконт")
                .build();
    }

    private ReplyKeyboardMarkup getReplyKeyboardMarkup() {
//		InlineKeyboardButton test = new InlineKeyboardButton("test");
//		test.setSwitchInlineQuery("test");

        KeyboardButton keyboardButton = new KeyboardButton();
        keyboardButton.setText("test1");

        KeyboardButton keyboardButton2 = new KeyboardButton();
        keyboardButton2.setText("test2");

        KeyboardButton keyboardButton3 = new KeyboardButton();
        keyboardButton3.setText("test3");

        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add(keyboardButton);
        keyboardRow.add(keyboardButton2);
        keyboardRow.add(keyboardButton3);

        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        keyboardRowList.add(keyboardRow);

        return ReplyKeyboardMarkup.builder()
                .keyboard(keyboardRowList)
                .inputFieldPlaceholder("???")
                .build();
    }

    private SendPoll getSendPoll(BinderContext binderContainer) {
        InlineKeyboardMarkup inlineKeyboardMarkup = getInlineKeyboardMarkup();

        return SendPoll.builder()
                .chatId(binderContainer.getChat().getChatName())
                .question("Вопрос такой-то?")
                .options(Lists.newArrayList("Первый ответ", "Второй ответ", "Третий ответ"))
                .type("regular")
//				.correctOptionId(1)
//				.allowMultipleAnswers(true)
//				.isClosed(true)
                .replyMarkup(inlineKeyboardMarkup)
                .build();
    }

    private InlineKeyboardMarkup getInlineKeyboardMarkup() {
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        rowsInLine.add(List.of(InlineKeyboardButton.builder().callbackData("123").text("321").build()));

        return InlineKeyboardMarkup.builder()
                .keyboard(rowsInLine)
                .build();
    }
}
