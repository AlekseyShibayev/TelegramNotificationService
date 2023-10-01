package com.company.app.telegram.component.binder.impl;

import com.company.app.core.util.Collections;
import com.company.app.telegram.TelegramFacade;
import com.company.app.telegram.component.binder.Binder;
import com.company.app.telegram.component.binder.BinderContext;
import com.company.app.telegram.domain.entity.Chat;
import com.company.app.wildberries_desire_lot.controller.WildberriesDesireController;
import com.company.app.wildberries_desire_lot.domain.entity.Desire;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WildberriesBinder implements Binder { // todo это какой-то конкретный биндер

    private static final String TYPE = "WB";

    private final WildberriesDesireController wildberriesDesireController;
    private final TelegramFacade telegramFacade;

    @Override
    public String getType() {
        return TYPE;
    }

    @SneakyThrows
    @Override
    public void bind(BinderContext binderContainer) {
        Chat chat = binderContainer.getChat();
        List<Desire> desireList = wildberriesDesireController.get(chat.getChatName()).getBody();

//        if (Collections.isEmpty(desireList)) {
//            telegramFacade.writeToTargetChat(chat.getChatName(), "nothing");
//        } else {
//            String message = desireList.stream()
//                    .map((Desire t) -> FoundItemDto.getLink(t))
//                    .distinct()
//                    .reduce((s, s2) -> s + "\n" + s2)
//                    .orElseThrow();
//            telegramFacade.writeToTargetChat(chat.getChatName(), message);
//        }
    }

}