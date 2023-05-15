package com.company.app.wildberries_searcher.impl;

import com.company.app.core.aop.logging.performance.PerformanceLogAnnotation;
import com.company.app.core.aop.logging.util.LogUtils;
import com.company.app.wildberries_desire_lot.component.data.ResponseProducts;
import com.company.app.wildberries_desire_lot.component.data.Size;
import com.company.app.wildberries_searcher.api.WildberriesSearcherAveragePriceExtractor;
import com.company.app.wildberries_searcher.api.WildberriesSearcherFilterer;
import com.company.app.wildberries_searcher.data.WildberriesSearcherContainer;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
public class WildberriesSearcherFiltererImpl implements WildberriesSearcherFilterer {

	private static final String GREED_INDEX = "1.30";
	private static final int MAX_PRICE = 5000_00;

	/**
	 * Нужно для логов. Будет только один поток, гарантирую.
	 */
	private int preparedProductsSize;
	private int filterPosition;

	@Autowired
	private WildberriesSearcherAveragePriceExtractor wildberriesSearcherAveragePriceExtractor;

	@PerformanceLogAnnotation
	@Override
	public List<ResponseProducts> filter(List<ResponseProducts> products, WildberriesSearcherContainer wildberriesSearcherContainer) {
		List<ResponseProducts> preparedProducts = products.stream()
				.filter(this::withRating)
				.filter(this::withFeedbacks)
				.filter(this::withMaxPrice)
				.filter(responseProducts -> withContainsSize(responseProducts, wildberriesSearcherContainer))
				.collect(Collectors.toList());

		if (log.isDebugEnabled()) {
			log.debug("[{}]: После предварительной фильтрации осталось [{}] шт.", wildberriesSearcherContainer.getChatId(), preparedProducts.size());
			preparedProductsSize = preparedProducts.size();
			filterPosition = 1;
		}

		return preparedProducts.stream()
				.filter(responseProducts -> withGoodPrice(responseProducts, wildberriesSearcherContainer))
				.collect(Collectors.toList());
	}

	private boolean withMaxPrice(ResponseProducts responseProducts) {
		Integer price = responseProducts.getSalePriceU();
		return price <= MAX_PRICE;
	}

	private boolean withRating(ResponseProducts responseProducts) {
		String rating = responseProducts.getRating();
		return Integer.parseInt(rating) >= 4;
	}

	private boolean withFeedbacks(ResponseProducts responseProducts) {
		String feedbacks = responseProducts.getFeedbacks();
		return Integer.parseInt(feedbacks) >= 10;
	}

	private boolean withContainsSize(ResponseProducts responseProducts, WildberriesSearcherContainer wildberriesSearcherContainer) {
		Optional<Size> optional = getUserSize(responseProducts, wildberriesSearcherContainer.getDressSize());
		return optional.isPresent();
	}

	Optional<Size> getUserSize(ResponseProducts responseProducts, String userSizes) {
		List<Size> productSizes = responseProducts.getSizes();
		Set<String> userSizesSet = Sets.newHashSet(userSizes.split(";"));
		return productSizes.stream()
				.filter(size -> userSizesSet.contains(size.getName()))
				.findAny();
	}

	private boolean withGoodPrice(ResponseProducts responseProducts, WildberriesSearcherContainer wildberriesSearcherContainer) {
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
