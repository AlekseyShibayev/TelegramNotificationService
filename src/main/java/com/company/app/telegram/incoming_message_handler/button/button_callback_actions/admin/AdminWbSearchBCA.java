package com.company.app.telegram.incoming_message_handler.button.button_callback_actions.admin;

import com.company.app.telegram.TelegramFacade;
import com.company.app.telegram.domain.entity.Chat;
import com.company.app.telegram.incoming_message_handler.button.model.ButtonCallbackAction;
import com.company.app.telegram.incoming_message_handler.button.model.ButtonCallbackActionContext;
import com.company.app.wildberries.knowledge.domain.entity.Supplier;
import com.company.app.wildberries.search.WbSearcherFacade;
import com.company.app.wildberries.search.model.WbSearchContext;
import com.company.app.wildberries.search.model.WbSearchResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class AdminWbSearchBCA implements ButtonCallbackAction {

    private static final String TYPE = "ADMIN_WB_SEARCH";

    private final TelegramFacade telegramFacade;
    private final WbSearcherFacade wildberriesSearcherFacade;

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

        WbSearchContext wbSearchContext = new WbSearchContext()
            .setChatName(chat.getChatName())
            .setBrand(supplierId);

        WbSearchResult result = wildberriesSearcherFacade.search(wbSearchContext);

        if (result.isSuccess()) {
//            Supplier supplier = wildberriesSupplierController.getBySupplierId(supplierId).getBody();
//            String message = String.format("Поисковая задача успешно запущена. [%s]", supplier.getSupplierName());
            String message = String.format("Поисковая задача успешно запущена. [%s]", incomingMessage);
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
//        List<Supplier> supplierList = wildberriesSupplierController.getAll().getBody();
        List<Supplier> supplierList = List.of(new Supplier().setSupplierId("921").setSupplierName("Tom Tailor")
                , new Supplier().setSupplierId("12845").setSupplierName("Envy Lab"));

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