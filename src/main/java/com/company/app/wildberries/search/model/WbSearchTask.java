package com.company.app.wildberries.search.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.company.app.telegram.TelegramFacade;
import com.company.app.wildberries.search.domain.dto.LinkDto;
import com.company.app.wildberries.search.service.WbSearcher;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Getter
@Setter
@Accessors(chain = true)
public class WbSearchTask implements Runnable {

    private WbSearchContext searcherContext;
    private WbSearcher wbSearcher;
    private TelegramFacade telegramFacade;
    private WbCallback wbCallback;

    @Override
    public void run() {
        try {
            startSearch();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            wbCallback.callback();
        }
    }

    private void startSearch() {
        List<LinkDto> result = getAllProducts();
        String endMessage = String.format("Поиск завершен. Найдено: [%s].", result.size());

        if (log.isDebugEnabled()) {
            log.debug("[{}]: [{}].", searcherContext.getChatName(), endMessage);
            result.forEach(dto -> log.debug("[{}]: [{}].", searcherContext.getChatName(), dto.toMessage()));
        }
        telegramFacade.writeToTargetChat(searcherContext.getChatName(), endMessage);
    }

    private List<LinkDto> getAllProducts() {
        List<LinkDto> result = new ArrayList<>();
        Arrays.stream(searcherContext.getBrand().split(";"))
            .forEach(supplier -> {
                searcherContext.setBrand(supplier);
                result.addAll(wbSearcher.search(this.searcherContext));
            });
        return result;
    }

}