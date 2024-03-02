package com.company.app.wildberries.search.service;

import javax.annotation.PostConstruct;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import com.company.app.telegram.TelegramFacade;
import com.company.app.wildberries.search.domain.entity.SearchData;
import com.company.app.wildberries.search.domain.repository.SearchDataRepository;
import com.company.app.wildberries.search.model.WbSearchContext;
import com.company.app.wildberries.search.model.WbSearchResult;
import com.company.app.wildberries.search.model.WbSearchTask;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class WbSearcherSemaphoreService {

    private ExecutorService executorService;
    private Semaphore semaphore;

    private final SearchDataRepository searchDataRepository;
    private final WbSearcher wbSearcher;
    private final TelegramFacade telegramFacade;

    @PostConstruct
    private void init() {
        executorService = Executors.newSingleThreadExecutor();
        semaphore = new Semaphore(1);
    }

    public WbSearchResult tryStart(WbSearchContext context) {
        if (semaphore.tryAcquire()) {
            return startNewAsyncSearch(context);
        } else {
            return new WbSearchResult()
                .setSuccess(false)
                .setMessage("Занято! Вы что 5 лет в разработке и ни разу не использовали семафор??? (c)");
        }
    }

    private WbSearchResult startNewAsyncSearch(WbSearchContext context) {
        String chatName = context.getChatName();
        Optional<SearchData> optional = searchDataRepository.findByChatName(chatName);
        if (optional.isEmpty()) {
            callback();
            return new WbSearchResult()
                .setSuccess(false)
                .setMessage("Нет информации о поиске, обратитесь к админу.");

        } else {
            WbSearchContext searcherContext = WbSearchContext.of(context, optional.get());
            log.debug("Запускаю поиск для [{}].", context);
            executorService.submit(new WbSearchTask()
                .setSearcherContext(searcherContext)
                .setTelegramFacade(telegramFacade)
                .setWbSearcher(wbSearcher)
                .setWbCallback(this::callback));

            return new WbSearchResult()
                .setSuccess(true);
        }
    }

    private void callback() {
        semaphore.release();
    }

}