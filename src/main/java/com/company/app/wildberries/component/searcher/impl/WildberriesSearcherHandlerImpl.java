package com.company.app.wildberries.component.searcher.impl;

import com.company.app.telegram.controller.TelegramController;
import com.company.app.wildberries.component.searcher.api.WildberriesSearcher;
import com.company.app.wildberries.component.searcher.api.WildberriesSearcherHandler;
import com.company.app.wildberries.component.searcher.data.WildberriesSearcherContainer;
import com.company.app.wildberries.component.searcher.data.WildberriesSearcherResult;
import com.company.app.wildberries.component.searcher.data.WildberriesSearcherTask;
import lombok.SneakyThrows;
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

	@SneakyThrows
	private void startNewAsyncSearch(WildberriesSearcherContainer wildberriesSearcherContainer) {
		log.debug("Запускаю поиск для [{}].", wildberriesSearcherContainer);
		executorService.submit(WildberriesSearcherTask.builder()
				.wildberriesSearcherContainer(wildberriesSearcherContainer)
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
