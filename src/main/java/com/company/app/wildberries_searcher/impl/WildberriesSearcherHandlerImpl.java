package com.company.app.wildberries_searcher.impl;

import com.company.app.telegram.controller.TelegramController;
import com.company.app.wildberries_searcher.api.WildberriesSearcher;
import com.company.app.wildberries_searcher.api.WildberriesSearcherHandler;
import com.company.app.wildberries_searcher.data.WildberriesSearcherContainer;
import com.company.app.wildberries_searcher.data.WildberriesSearcherResult;
import com.company.app.wildberries_searcher.data.WildberriesSearcherTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Component
public class WildberriesSearcherHandlerImpl implements WildberriesSearcherHandler {

	@Autowired
	private WildberriesSearcher wildberriesSearcher;
	@Autowired
	private TelegramController telegramController;

	private ExecutorService executorService;

	@Override
	public WildberriesSearcherResult process(WildberriesSearcherContainer wildberriesSearcherContainer) {
		if (executorService == null) {
			startNewAsyncSearch(wildberriesSearcherContainer);
			return WildberriesSearcherResult.builder().isSuccess(true).build();
		} else {
			return WildberriesSearcherResult.builder().isSuccess(false).build();
		}
	}

	private void startNewAsyncSearch(WildberriesSearcherContainer wildberriesSearcherContainer) {
		log.debug("Lock получен. Запускаю поиск для [{}].", wildberriesSearcherContainer);
		executorService = Executors.newSingleThreadExecutor();
		executorService.submit(WildberriesSearcherTask.builder()
				.wildberriesSearcherContainer(wildberriesSearcherContainer)
				.telegramController(telegramController)
				.wildberriesSearcher(wildberriesSearcher)
				.executorService(executorService)
				.build());
	}
}
