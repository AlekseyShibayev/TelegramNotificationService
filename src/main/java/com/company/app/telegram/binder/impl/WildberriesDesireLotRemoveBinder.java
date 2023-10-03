package com.company.app.telegram.binder.impl;

import com.company.app.core.infrastructure.entitygraphextractor.EntityGraphExtractor;
import com.company.app.core.util.Strings;
import com.company.app.telegram.TelegramFacade;
import com.company.app.telegram.binder.Binder;
import com.company.app.telegram.binder.component.BinderContext;
import com.company.app.telegram.domain.entity.Chat;
import com.company.app.wildberries_desire_lot.domain.entity.Desire;
import com.company.app.wildberries_desire_lot.domain.entity.DesireLot;
import com.company.app.wildberries_desire_lot.domain.repository.DesireRepository;
import com.company.app.wildberries_desire_lot.domain.specification.DesireSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class WildberriesDesireLotRemoveBinder implements Binder {

    private static final String TYPE = "WB_DL_R";

    private final TelegramFacade telegramFacade;
    private final DesireRepository desireRepository;
    private final EntityGraphExtractor entityGraphExtractor;

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public void bind(BinderContext binderContext) {
        execute(binderContext);
    }

    public void execute(BinderContext binderContext) {
        Chat chat = binderContext.getChat();
        String incomingMessage = binderContext.getMessage();

        if (isFirstTimeHere(incomingMessage)) {
            showButtons(chat);
        } else {
            telegramFacade.writeToTargetChat(chat.getChatName(), "в разработке");
        }
    }

    private void showButtons(Chat chat) {
        List<Desire> desires = desireRepository.findAll(DesireSpecification.chatNameIs(chat.getChatName()));
        List<Desire> extractedDesires = entityGraphExtractor.createDesireContext(desires)
                .withDesireLot()
                .extractAll();

        List<String> names = extractedDesires.stream()
                .map(desire -> {
                    String article = desire.getArticle();
                    BigDecimal desirePrice = desire.getPrice();

                    DesireLot desireLot = desire.getDesireLot();
                    String description = desireLot == null ? "описания ещё нет" : desireLot.getDescription();
                    return article + " " + Strings.cutEnd(desirePrice.toString(), 3) + " " + description;
                }).toList();

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chat.getChatName());
        sendMessage.setText("Выберите что удалить:");
        sendMessage.setReplyMarkup(getButtons(names));
//        sendMessage.setReplyMarkup(getButtons(List.of("1", "2", "3")));
        telegramFacade.writeToTargetChat(sendMessage);
    }

    private InlineKeyboardMarkup getButtons(List<String> strings) {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        markupInline.setKeyboard(getRowsInLine(strings));
        return markupInline;
    }

    private List<List<InlineKeyboardButton>> getRowsInLine(List<String> strings) {
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        for (String string : strings) {
            string = string.substring(0, 35);
            InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton(string);
            inlineKeyboardButton.setCallbackData(TYPE + Binder.BINDER_DELIMITER + string);
            rowsInLine.add(List.of(inlineKeyboardButton));
        }
        return rowsInLine;
    }


}
