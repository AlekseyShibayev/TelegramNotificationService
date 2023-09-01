package com.company.app.wildberries_searcher.component;

import com.company.app.core.data.ResponseProducts;
import com.company.app.telegram.controller.TelegramController;
import com.company.app.telegram.domain.dto.TargetMessage;
import com.company.app.wildberries_searcher.component.data.WildberriesSearcherContext;
import com.company.app.wildberries_searcher.domain.dto.WildberriesLinkDto;
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
