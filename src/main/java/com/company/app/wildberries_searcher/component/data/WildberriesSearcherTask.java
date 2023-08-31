package com.company.app.wildberries_searcher.component.data;

import com.company.app.core.util.Logs;
import com.company.app.telegram.controller.TelegramController;
import com.company.app.telegram.domain.dto.TargetMessage;
import com.company.app.wildberries_searcher.component.WildberriesSearcher;
import com.company.app.wildberries_searcher.domain.dto.WildberriesLinkDto;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Data
@Builder
public class WildberriesSearcherTask implements Runnable {

    private WildberriesSearcherContext wildberriesSearcherContainer;
    private WildberriesSearcher wildberriesSearcher;
    private TelegramController telegramController;
    private WildberriesSearcherCallback callBack;

    @Override
    public void run() {
        try {
            doWork();
        } catch (Exception e) {
            Logs.doExceptionLog(log, e);
        } finally {
            callBack.callback();
        }
    }

    private void doWork() {
        List<WildberriesLinkDto> result = getAllProducts();
        String endMessage = String.format("Поиск завершен. Найдено: [%s].", result.size());

        if (log.isDebugEnabled()) {
            log.debug("[{}]: [{}].", wildberriesSearcherContainer.getChatName(), endMessage);
            result.forEach(dto -> log.debug("[{}]: [{}].", wildberriesSearcherContainer.getChatName(), dto.toMessage()));
        }

        telegramController.say(TargetMessage.builder()
                .chatName(wildberriesSearcherContainer.getChatName())
                .message(endMessage)
                .build());
    }

    private List<WildberriesLinkDto> getAllProducts() {
        List<WildberriesLinkDto> result = new ArrayList<>();
        Arrays.stream(wildberriesSearcherContainer.getSupplier().split(";"))
                .forEach(supplier -> {
                    wildberriesSearcherContainer.setSupplier(supplier);
                    result.addAll(wildberriesSearcher.search(this.wildberriesSearcherContainer));
                });
        return result;
    }
}
