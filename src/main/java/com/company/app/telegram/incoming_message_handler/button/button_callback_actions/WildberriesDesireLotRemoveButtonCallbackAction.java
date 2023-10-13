package com.company.app.telegram.incoming_message_handler.button.button_callback_actions;

import com.company.app.core.infrastructure.entitygraphextractor.EntityGraphExtractor;
import com.company.app.core.util.Strings;
import com.company.app.telegram.TelegramFacade;
import com.company.app.telegram.domain.entity.Chat;
import com.company.app.telegram.incoming_message_handler.button.model.ButtonCallbackAction;
import com.company.app.telegram.incoming_message_handler.button.model.ButtonCallbackActionContext;
import com.company.app.wildberries_desire_lot.domain.entity.Desire;
import com.company.app.wildberries_desire_lot.domain.entity.DesireLot;
import com.company.app.wildberries_desire_lot.domain.repository.DesireRepository;
import com.company.app.wildberries_desire_lot.domain.specification.DesireSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class WildberriesDesireLotRemoveButtonCallbackAction implements ButtonCallbackAction {

    private static final String TYPE = "WB_DL_REMOVE";

    private final TelegramFacade telegramFacade;
    private final DesireRepository desireRepository;
    private final EntityGraphExtractor entityGraphExtractor;

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public void doAction(ButtonCallbackActionContext context) {
        Chat chat = context.getChat();
        String incomingMessage = context.getMessage();

        if (isFirstTimeHere(incomingMessage)) {
            showButtons(chat);
        } else {
            List<String> list = Arrays.stream(incomingMessage.split(BINDER_DELIMITER)).toList();
            String article = list.get(1);
            String price = list.get(2);
            Optional<Desire> one = desireRepository.findOne(DesireSpecification.chatNameIs(chat.getChatName())
                    .and(DesireSpecification.articleIs(article))
                    .and(DesireSpecification.priceIs(new BigDecimal(price)))
            );
            one.ifPresent(desire -> {
                desireRepository.delete(desire);
                telegramFacade.writeToTargetChat(chat.getChatName(), "%s удалил".formatted(article));
            });
        }
    }

    private void showButtons(Chat chat) {
        List<Desire> desires = desireRepository.findAll(DesireSpecification.chatNameIs(chat.getChatName()));

        if (CollectionUtils.isEmpty(desires)) {
            telegramFacade.writeToTargetChat(chat.getChatName(), "Список желаний пуст");
        } else {
            List<Desire> extractedDesires = entityGraphExtractor.createDesireContext(desires)
                    .withDesireLot()
                    .extractAll();

            InlineKeyboardMarkup markupInline = getInlineKeyboardMarkup(extractedDesires);

            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chat.getChatName());
            sendMessage.setText("Выберите что удалить:");
            sendMessage.setReplyMarkup(markupInline);
            telegramFacade.writeToTargetChat(sendMessage);
        }
    }

    private InlineKeyboardMarkup getInlineKeyboardMarkup(List<Desire> extractedDesires) {
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        for (Desire desire : extractedDesires) {
            String article = desire.getArticle();
            BigDecimal desirePrice = desire.getPrice();
            DesireLot desireLot = desire.getDesireLot();
            String description = desireLot == null ? "Описания ещё нет" : desireLot.getDescription();
            String string = article + " " + Strings.cutEnd(desirePrice.toString(), 3) + " " + description;

            InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton(string);
            inlineKeyboardButton.setCallbackData(TYPE + ButtonCallbackAction.BINDER_DELIMITER + article + ButtonCallbackAction.BINDER_DELIMITER + desire.getPrice());
            rowsInLine.add(List.of(inlineKeyboardButton));
        }

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        markupInline.setKeyboard(rowsInLine);
        return markupInline;
    }

}
