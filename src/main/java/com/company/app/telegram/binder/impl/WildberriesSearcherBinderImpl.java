package com.company.app.telegram.binder.impl;

import com.company.app.telegram.binder.BinderContainer;
import com.company.app.telegram.binder.api.WildberriesBinder;
import com.company.app.telegram.component.TelegramFacade;
import com.company.app.telegram.domain.entity.Chat;
import com.company.app.wildberries_knowledge.controller.WildberriesSupplierController;
import com.company.app.wildberries_knowledge.domain.entity.Supplier;
import com.company.app.wildberries_searcher.component.data.WildberriesSearcherContext;
import com.company.app.wildberries_searcher.component.data.WildberriesSearcherResult;
import com.company.app.wildberries_searcher.controller.WildberriesSearcherController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class WildberriesSearcherBinderImpl implements WildberriesBinder {

    private static final String TYPE = "WB_SEARCH";

    @Autowired
    private TelegramFacade telegramFacade;
    @Autowired
    private WildberriesSearcherController wildberriesSearcherController;
    @Autowired
    private WildberriesSupplierController wildberriesSupplierController;

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public void bind(BinderContainer binderContainer) {
        Chat chat = binderContainer.getChat();
        String incomingMessage = binderContainer.getMessage();

        if (isFirstTimeHere(incomingMessage)) {
            showButtonsWithSuppliers(chat);
        } else {
            tryStartSearch(chat, incomingMessage);
        }
    }

    private void tryStartSearch(Chat chat, String incomingMessage) {
        String supplierId = getSupplierId(incomingMessage);

        WildberriesSearcherContext wildberriesSearcherContainer = WildberriesSearcherContext.builder()
                .chatName(chat.getChatName())
                .supplier(supplierId)
                .build();

        WildberriesSearcherResult result = wildberriesSearcherController.search(wildberriesSearcherContainer).getBody();

        if (result.isSuccess()) {
            Supplier supplier = wildberriesSupplierController.getBySupplierId(supplierId).getBody();
            String message = String.format("Поисковая задача успешно запущена. [%s]", supplier.getSupplierName());
            telegramFacade.writeToTargetChat(chat.getChatName(), message);
        } else {
            telegramFacade.writeToTargetChat(chat.getChatName(), result.getMessage());
        }
    }

    private boolean isFirstTimeHere(String incomingMessage) {
        return !incomingMessage.contains(BINDER_DELIMITER);
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
