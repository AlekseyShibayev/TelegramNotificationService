package com.company.app.wildberries_searcher.component.impl;

import com.company.app.infrastructure.data.ResponseProducts;
import com.company.app.telegram.controller.TelegramController;
import com.company.app.telegram.domain.dto.TargetMessage;
import com.company.app.wildberries_searcher.component.api.WildberriesSearcherNotificator;
import com.company.app.wildberries_searcher.component.data.WildberriesSearcherContext;
import com.company.app.wildberries_searcher.domain.dto.WildberriesLinkDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WildberriesSearcherNotificatorImpl implements WildberriesSearcherNotificator {

    @Autowired
    private TelegramController telegramController;

    @Override
    public void notify(ResponseProducts responseProducts, WildberriesSearcherContext wildberriesSearcherContainer) {
        WildberriesLinkDto dto = responseProducts.toLinkDto();
        telegramController.say(TargetMessage.builder()
                .chatName(wildberriesSearcherContainer.getChatName())
                .message(dto.toMessage())
                .build());
    }
}
