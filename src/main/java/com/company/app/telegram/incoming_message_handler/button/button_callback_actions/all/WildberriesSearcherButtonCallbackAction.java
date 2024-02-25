package com.company.app.telegram.incoming_message_handler.button.button_callback_actions.all;

import java.util.ArrayList;
import java.util.List;

import com.company.app.telegram.TelegramFacade;
import com.company.app.telegram.domain.entity.Chat;
import com.company.app.telegram.incoming_message_handler.button.model.ButtonCallbackAction;
import com.company.app.telegram.incoming_message_handler.button.model.ButtonCallbackActionContext;
import com.company.app.wildberries.knowledge.controller.WildberriesSupplierController;
import com.company.app.wildberries.knowledge.domain.entity.Supplier;
import com.company.app.wildberries.search.WbSearcherFacade;
import com.company.app.wildberries.search.model.WbSearchContext;
import com.company.app.wildberries.search.model.WbSearchResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;


@Service
@RequiredArgsConstructor
public class WildberriesSearcherButtonCallbackAction implements ButtonCallbackAction {

    private static final String TYPE = "WB_SEARCH";

    private final TelegramFacade telegramFacade;
    private final WbSearcherFacade wildberriesSearcherFacade;
    private final WildberriesSupplierController wildberriesSupplierController;

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public void doAction(ButtonCallbackActionContext context) {
        Chat chat = context.getChat();
        String incomingMessage = context.getMessage();

        if (isFirstTimeHere(incomingMessage)) {
            showButtonsWithSuppliers(chat);
        } else {
            tryStartSearch(chat, incomingMessage);
        }
    }

    private void tryStartSearch(Chat chat, String incomingMessage) {
        String supplierId = getSupplierId(incomingMessage);

        WbSearchContext wildberriesSearcherContainer = new WbSearchContext()
            .setChatName(chat.getChatName())
            .setBrand(supplierId);

        WbSearchResult result = wildberriesSearcherFacade.search(wildberriesSearcherContainer);

        if (result.isSuccess()) {
            Supplier supplier = wildberriesSupplierController.getBySupplierId(supplierId).getBody();
            String message = String.format("Поисковая задача успешно запущена. [%s]", supplier.getSupplierName());
            telegramFacade.writeToTargetChat(chat.getChatName(), message);
        } else {
            telegramFacade.writeToTargetChat(chat.getChatName(), result.getMessage());
        }
    }

    private String getSupplierId(String incomingMessage) {
        String[] split = incomingMessage.split(BINDER_DELIMITER);
        return split[1];
    }

    private void showButtonsWithSuppliers(Chat chat) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chat.getChatName());
        sendMessage.setText("Выберите производителя:");
        sendMessage.setReplyMarkup(getButtons());
        telegramFacade.writeToTargetChat(sendMessage);
    }

    private InlineKeyboardMarkup getButtons() {
        List<Supplier> supplierList = wildberriesSupplierController.getAll().getBody();
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        markupInline.setKeyboard(getRowsInLine(supplierList));
        return markupInline;
    }

    private List<List<InlineKeyboardButton>> getRowsInLine(List<Supplier> supplierList) {
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        for (Supplier supplier : supplierList) {
            InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton(supplier.getSupplierName());
            inlineKeyboardButton.setCallbackData(TYPE + BINDER_DELIMITER + supplier.getSupplierId());
            rowsInLine.add(List.of(inlineKeyboardButton));
        }
        return rowsInLine;
    }

}
