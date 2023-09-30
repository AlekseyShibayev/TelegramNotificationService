package com.company.app.telegram.component.binder.impl;

import com.company.app.telegram.TelegramFacade;
import com.company.app.telegram.component.binder.Binder;
import com.company.app.telegram.component.binder.BinderContext;
import com.company.app.telegram.domain.entity.Chat;
import com.company.app.wildberries_desire_lot.controller.WildberriesController;
import com.company.app.wildberries_desire_lot.domain.dto.FoundItemDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WildberriesBinder implements Binder { // todo это какой-то конкретный биндер

    private static final String TYPE = "WB";

    private final WildberriesController wildberriesController;
    private final TelegramFacade telegramFacade;

    @Override
    public String getType() {
        return TYPE;
    }

    @SneakyThrows
    @Override
    public void bind(BinderContext binderContainer) {
        Chat chat = binderContainer.getChat();
//        List<FoundItemDto> foundItemDtoList = wildberriesController.getAllFoundItems().getBody();
        List<FoundItemDto> foundItemDtoList = new ArrayList<>();
        if (CollectionUtils.isEmpty(foundItemDtoList)) {
            telegramFacade.writeToTargetChat(chat.getChatName(), "Пусто");
        } else {
            String message = foundItemDtoList.stream()
                    .map(FoundItemDto::getLink)
                    .distinct()
                    .reduce((s, s2) -> s + "\n" + s2)
                    .orElseThrow();
            telegramFacade.writeToTargetChat(chat.getChatName(), message);
        }
    }

}