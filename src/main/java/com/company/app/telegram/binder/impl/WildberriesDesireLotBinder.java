package com.company.app.telegram.binder.impl;

import com.company.app.core.util.Collections;
import com.company.app.telegram.TelegramFacade;
import com.company.app.telegram.binder.Binder;
import com.company.app.telegram.binder.component.BinderContext;
import com.company.app.telegram.domain.entity.Chat;
import com.company.app.wildberries_desire_lot.controller.WildberriesDesireController;
import com.company.app.wildberries_desire_lot.domain.dto.FulfilledDesire;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WildberriesDesireLotBinder implements Binder {

    private static final String TYPE = "WB_DESIRE_LOT";

    private final WildberriesDesireController wildberriesDesireController;
    private final TelegramFacade telegramFacade;

    @Override
    public String getType() {
        return TYPE;
    }

    @SneakyThrows
    @Override
    public void bind(BinderContext binderContext) {
        Chat chat = binderContext.getChat();
        List<FulfilledDesire> desireList = wildberriesDesireController.getFulfilledDesires(chat.getChatName()).getBody();

        if (Collections.isEmpty(desireList)) {
            telegramFacade.writeToTargetChat(chat.getChatName(), "Ничего не нашёл");
        } else {
            desireList.forEach(fulfilledDesire ->
                    telegramFacade.writeToTargetChat(fulfilledDesire.getChatName(), fulfilledDesire.getUrl()));
        }
    }

}