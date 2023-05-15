package com.company.app.wildberries_searcher.impl;

import com.company.app.core.aop.logging.performance.PerformanceLogAnnotation;
import com.company.app.core.aop.logging.util.LogUtils;
import com.company.app.wildberries_desire_lot.component.data.ResponseProducts;
import com.company.app.wildberries_searcher.api.WildberriesSearcherAveragePriceExtractor;
import com.company.app.wildberries_searcher.api.WildberriesSearcherFilterer;
import com.company.app.wildberries_searcher.data.WildberriesSearcherContainer;
import com.company.app.wildberries_searcher.data.filter.WildberriesSearcherFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class WildberriesSearcherFiltererImpl implements WildberriesSearcherFilterer {

	private static final String GREED_INDEX = "1.40";

	/**
	 * Нужно для логов. Будет только один поток, гарантирую.
	 */
	private int preparedProductsSize;
	private int filterPosition;

	@Autowired
	private WildberriesSearcherAveragePriceExtractor wildberriesSearcherAveragePriceExtractor;
	@Autowired
	private List<WildberriesSearcherFilter> wildberriesSearcherFilterList;

	@PerformanceLogAnnotation
	@Override
	public List<ResponseProducts> filter(List<ResponseProducts> products, WildberriesSearcherContainer wildberriesSearcherContainer) {
		List<ResponseProducts> preparedProducts = products.stream()
				.filter(responseProducts -> filterAll(responseProducts, wildberriesSearcherContainer))
				.collect(Collectors.toList());

		if (log.isDebugEnabled()) {
			log.debug("[{}]: После предварительной фильтрации осталось [{}] шт.", wildberriesSearcherContainer.getChatId(), preparedProducts.size());
			preparedProductsSize = preparedProducts.size();
			filterPosition = 1;
		}

		return preparedProducts.stream()
				.filter(responseProducts -> currentPriceLesserThanAveragePrice(responseProducts, wildberriesSearcherContainer))
				.collect(Collectors.toList());
	}

	private boolean filterAll(ResponseProducts responseProducts, WildberriesSearcherContainer wildberriesSearcherContainer) {
		for (WildberriesSearcherFilter filter : wildberriesSearcherFilterList) {
			if (filter.isPreFilter()) {
				boolean result = filter.doFilter(responseProducts, wildberriesSearcherContainer);
				if (!result) {
					return false;
				}
			}
		}
		return true;
	}

	private boolean currentPriceLesserThanAveragePrice(ResponseProducts responseProducts, WildberriesSearcherContainer wildberriesSearcherContainer) {
		try {
			BigDecimal averagePrice = new BigDecimal(wildberriesSearcherAveragePriceExtractor.getAveragePrice(responseProducts));
			BigDecimal currentPrice = new BigDecimal(responseProducts.getSalePriceU());
			currentPrice = currentPrice.multiply(new BigDecimal(GREED_INDEX));
			doLog(wildberriesSearcherContainer, responseProducts, averagePrice, currentPrice);
			int i = currentPrice.compareTo(averagePrice);
			return i < 0;
		} catch (Exception exception) {
			LogUtils.doExceptionLog(exception, String.format("[%s] Проблема с [%s]:", wildberriesSearcherContainer.getChatId(), responseProducts.getId()));
			return false;
		}
	}

	private void doLog(WildberriesSearcherContainer wildberriesSearcherContainer, ResponseProducts responseProducts, BigDecimal averagePrice, BigDecimal currentPrice) {
		if (log.isDebugEnabled()) {
			log.debug("[{}]: [{}]: Цена: текущая*[{}]: [{}] < средняя: [{}] ? [{}/{}]",
					wildberriesSearcherContainer.getChatId(), responseProducts.getId(), GREED_INDEX, currentPrice, averagePrice, filterPosition, preparedProductsSize);
			filterPosition++;
		}
	}
}
