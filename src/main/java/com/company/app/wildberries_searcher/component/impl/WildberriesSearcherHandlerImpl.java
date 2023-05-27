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
			startNewAsyncSearch(wildberriesSearcherContainer);
			return getResult(true);
		} else {
			return getResult(false);
		}
	}

	private void startNewAsyncSearch(WildberriesSearcherContainer wildberriesSearcherContainer) {
		log.debug("Запускаю поиск для [{}].", wildberriesSearcherContainer);

		String chatName = wildberriesSearcherContainer.getChatName();
		SearchData searchData = searchDataService.getSearchData(chatName);
		WildberriesSearcherContainer container = WildberriesSearcherContainer.of(wildberriesSearcherContainer, searchData);

		executorService.submit(WildberriesSearcherTask.builder()
				.wildberriesSearcherContainer(container)
				.telegramController(telegramController)
				.wildberriesSearcher(wildberriesSearcher)
				.callBack(this::callback)
				.build());
	}

	private void callback() {
		semaphore.release();
	}

	private WildberriesSearcherResult getResult(boolean isSuccess) {
		return WildberriesSearcherResult.builder().isSuccess(isSuccess).build();
	}
}
