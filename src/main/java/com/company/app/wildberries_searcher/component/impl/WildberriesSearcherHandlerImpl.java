package com.company.app.wildberries_searcher.component.impl;

import com.company.app.telegram.controller.TelegramController;
import com.company.app.wildberries_searcher.component.api.WildberriesSearcher;
import com.company.app.wildberries_searcher.component.api.WildberriesSearcherHandler;
import com.company.app.wildberries_searcher.component.data.WildberriesSearcherContainer;
import com.company.app.wildberries_searcher.component.data.WildberriesSearcherResult;
import com.company.app.wildberries_searcher.component.data.WildberriesSearcherTask;
import com.company.app.wildberries_searcher.domain.entity.SearchData;
import com.company.app.wildberries_searcher.domain.service.api.SearchDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

@Slf4j
@Component
public class WildberriesSearcherHandlerImpl implements WildberriesSearcherHandler {

    @Autowired
    private SearchDataService searchDataService;
    @Autowired
    private WildberriesSearcher wildberriesSearcher;
    @Autowired
    private TelegramController telegramController;

    private ExecutorService executorService;
    private Semaphore semaphore;

    @PostConstruct
    private void init() {
        executorService = Executors.newSingleThreadExecutor();
        semaphore = new Semaphore(1);
    }

    @Override
    public WildberriesSearcherResult process(WildberriesSearcherContainer wildberriesSearcherContainer) {
        if (semaphore.tryAcquire()) {
            return startNewAsyncSearch(wildberriesSearcherContainer);
        } else {
            return WildberriesSearcherResult.builder()
                    .isSuccess(false)
                    .message("Занято! Вы что 5 лет в разработке и ни разу не использовали семафор???")
                    .build();
        }
    }

    private WildberriesSearcherResult startNewAsyncSearch(WildberriesSearcherContainer wildberriesSearcherContainer) {
        String chatName = wildberriesSearcherContainer.getChatName();
        SearchData searchData = searchDataService.getSearchData(chatName);
        if (searchData == null) {
            callback();
            return WildberriesSearcherResult.builder()
                    .isSuccess(false)
                    .message("Нет информации о поиске, обратитесь к админу.")
                    .build();
        } else {
            WildberriesSearcherContainer container = WildberriesSearcherContainer.of(wildberriesSearcherContainer, searchData);
            log.debug("Запускаю поиск для [{}].", wildberriesSearcherContainer);
            executorService.submit(WildberriesSearcherTask.builder()
                    .wildberriesSearcherContainer(container)
                    .telegramController(telegramController)
                    .wildberriesSearcher(wildberriesSearcher)
                    .callBack(this::callback)
                    .build());
            return WildberriesSearcherResult.builder().isSuccess(true).build();
        }
    }

    private void callback() {
        semaphore.release();
    }
}
