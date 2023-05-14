package com.company.app.wildberries_searcher.impl;

import com.company.app.wildberries_desire_lot.component.data.ResponseProducts;
import com.company.app.wildberries_desire_lot.domain.dto.WildberriesLinkDto;
import com.company.app.wildberries_searcher.api.WildberriesSearcher;
import com.company.app.wildberries_searcher.api.WildberriesSearcherExtractor;
import com.company.app.wildberries_searcher.api.WildberriesSearcherFilterer;
import com.company.app.wildberries_searcher.data.WildberriesSearcherContainer;
import com.company.app.wildberries_searcher.data.WildberriesSearcherResult;
import com.company.app.wildberries_searcher.util.WildberriesSearcherProductsUrlCreator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

@Slf4j
@Component
public class WildberriesSearcherImpl implements WildberriesSearcher {

	Lock lock = new ReentrantLock();

	@Autowired
	private WildberriesSearcherExtractor wildberriesSearcherExtractor;
	@Autowired
	private WildberriesSearcherFilterer wildberriesSearcherFilterer;

	@Override
	public WildberriesSearcherResult search(WildberriesSearcherContainer wildberriesSearcherContainer) {
		try {
			if (lock.tryLock()) {
				lock.lock();
				log.debug("Lock получен. Запускаю поиск для [{}].", wildberriesSearcherContainer);
				return WildberriesSearcherResult.builder()
						.isSuccess(true)
						.wildberriesLinkDtoList(getWildberriesLinkDtos(wildberriesSearcherContainer))
						.build();
			} else {
				return WildberriesSearcherResult.builder().isSuccess(false).build();
			}
		} finally {
			lock.unlock();
		}
	}

	private List<WildberriesLinkDto> getWildberriesLinkDtos(WildberriesSearcherContainer wildberriesSearcherContainer) {
		String url = WildberriesSearcherProductsUrlCreator.createUrl(wildberriesSearcherContainer);
		List<ResponseProducts> products = wildberriesSearcherExtractor.extractResponseProducts(url);
		List<ResponseProducts> filteredProducts = wildberriesSearcherFilterer.filter(products, wildberriesSearcherContainer);
		return filteredProducts.stream()
				.map(ResponseProducts::to)
				.distinct()
				.collect(Collectors.toList());
	}
}
