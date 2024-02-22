package com.company.app.telegram.incoming_message_handler.button.button_callback_actions.admin;

import com.company.app.telegram.TelegramFacade;
import com.company.app.telegram.domain.entity.Chat;
import com.company.app.telegram.incoming_message_handler.button.model.ButtonCallbackAction;
import com.company.app.telegram.incoming_message_handler.button.model.ButtonCallbackActionContext;
import com.company.app.wildberries.common.price_history.WbHistoryFinder;
import com.company.app.wildberries.common.price_history.domain.entity.Product;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class AdminWbSearchButtonCallbackAction implements ButtonCallbackAction {

    private static final String TYPE = "ADMIN_WB_SEARCH";

    private final WbHistoryFinder wbHistoryFinder;
    private final TelegramFacade telegramFacade;

    @Override
    public String getType() {
        return TYPE;
    }

    @SneakyThrows
    @Override
    public void doAction(ButtonCallbackActionContext context) {
        Chat chat = context.getChat();
        Product product = wbHistoryFinder.findHistoryBy("95452679");
        telegramFacade.writeToTargetChat(chat.getChatName(), "product with article [%s] saved".formatted(product.getArticle()));
    }

}