package com.company.app.wildberries.search.service;

import com.company.app.telegram.controller.TelegramController;
import com.company.app.telegram.domain.dto.TargetMessage;
import com.company.app.wildberries.common.model.ResponseProducts;
import com.company.app.wildberries.search.domain.dto.WildberriesLinkDto;
import com.company.app.wildberries.search.service.data.WildberriesSearcherContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class WildberriesSearcherNotificator {

    private final TelegramController telegramController;

    public void notify(ResponseProducts responseProducts, WildberriesSearcherContext wildberriesSearcherContainer) {
        WildberriesLinkDto dto = responseProducts.toLinkDto();
        telegramController.say(TargetMessage.builder()
            .chatName(wildberriesSearcherContainer.getChatName())
            .message(dto.toMessage())
            .build());
    }

}
