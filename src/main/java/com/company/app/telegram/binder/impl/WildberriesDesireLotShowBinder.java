package com.company.app.telegram.binder.impl;

import com.company.app.core.util.Collections;
import com.company.app.telegram.TelegramFacade;
import com.company.app.telegram.binder.Binder;
import com.company.app.telegram.binder.component.BinderContext;
import com.company.app.telegram.domain.entity.Chat;
import com.company.app.wildberries_desire_lot.controller.WildberriesDesireController;
import com.company.app.wildberries_desire_lot.controller.dto.FulfilledDesire;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WildberriesDesireLotShowBinder implements Binder {

    private static final String TYPE = "WB_DL_S";

    private final WildberriesDesireController wildberriesDesireController;
    private final TelegramFacade telegramFacade;

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public void bind(BinderContext binderContext) {
        show(binderContext);
    }

    private void show(BinderContext binderContext) {
        Chat chat = binderContext.getChat();

        List<FulfilledDesire> desireList = wildberriesDesireController.getFulfilledDesires(chat.getChatName()).getBody();

        if (Collections.isEmpty(desireList)) {
            telegramFacade.writeToTargetChat(chat.getChatName(), "Ничего не нашёл");
        } else  {
            desireList.forEach(fulfilledDesire ->
                    telegramFacade.writeToTargetChat(fulfilledDesire.getChatName(), fulfilledDesire.getUrl()));
        }
    }

}