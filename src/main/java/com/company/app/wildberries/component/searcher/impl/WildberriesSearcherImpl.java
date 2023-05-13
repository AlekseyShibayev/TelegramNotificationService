package com.company.app.wildberries.component.searcher.impl;

import com.company.app.core.aop.logging.util.LogUtils;
import com.company.app.wildberries.component.data.ResponseProducts;
import com.company.app.wildberries.component.data.Size;
import com.company.app.wildberries.component.searcher.api.WildberriesSearcher;
import com.company.app.wildberries.component.searcher.api.WildberriesSearcherAveragePriceExtractor;
import com.company.app.wildberries.component.searcher.api.WildberriesSearcherExtractor;
import com.company.app.wildberries.component.searcher.data.WildberriesSearcherContainer;
import com.company.app.wildberries.component.searcher.util.WildberriesSearcherProductsUrlCreator;
import com.company.app.wildberries.domain.dto.WildberriesLinkDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
public class WildberriesSearcherImpl implements WildberriesSearcher {

	private static final String TEMP_NAME = "1.20";

	@Autowired
	private WildberriesSearcherExtractor wildberriesSearcherExtractor;
	@Autowired
	private WildberriesSearcherAveragePriceExtractor wildberriesSearcherAveragePriceExtractor;

	@Override
	public List<WildberriesLinkDto> search(WildberriesSearcherContainer wildberriesSearcherContainer) {
		String url = WildberriesSearcherProductsUrlCreator.createUrl(wildberriesSearcherContainer);
		List<ResponseProducts> products = wildberriesSearcherExtractor.extractResponseProducts(url);

		List<ResponseProducts> preparedProducts = products.stream()
				.filter(this::withRating)
				.filter(this::withFeedbacks)
				.filter(responseProducts -> withContainsSize(responseProducts, wildberriesSearcherContainer))
				.collect(Collectors.toList());
		log.debug("[{}]: Запускаю фильтрацию для [{}] шт.", wildberriesSearcherContainer.getChatId(), preparedProducts.size());

		return preparedProducts.stream()
				.filter(responseProducts -> withGoodPrice(responseProducts, wildberriesSearcherContainer))
				.map(ResponseProducts::to)
				.distinct()
				.collect(Collectors.toList());
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

	private Optional<Size> getUserSize(ResponseProducts responseProducts, String userSize) {
		List<Size> sizes = responseProducts.getSizes();
		return sizes.stream()
				.filter(size -> size.getName().equals(userSize))
				.findAny();
	}

	private boolean withGoodPrice(ResponseProducts responseProducts, WildberriesSearcherContainer wildberriesSearcherContainer) {
		try {
			BigDecimal averagePrice = new BigDecimal(wildberriesSearcherAveragePriceExtractor.getAveragePrice(responseProducts));
			BigDecimal currentPrice = new BigDecimal(responseProducts.getSalePriceU());
			currentPrice = currentPrice.multiply(new BigDecimal(TEMP_NAME));
			doLog(wildberriesSearcherContainer, averagePrice, currentPrice);
			int i = currentPrice.compareTo(averagePrice);
			return i < 0;
		} catch (Exception exception) {
			LogUtils.doExceptionLog(exception, String.format("[%s] Проблема с [%s]:", wildberriesSearcherContainer.getChatId(), responseProducts.getId()));
			return false;
		}
	}

	private void doLog(WildberriesSearcherContainer wildberriesSearcherContainer, BigDecimal averagePrice, BigDecimal currentPrice) {
		if (log.isDebugEnabled()) {
			log.debug("[{}]: Цена: текущая*[{}]: [{}] < средняя: [{}] ?",
					wildberriesSearcherContainer.getChatId(), TEMP_NAME, currentPrice, averagePrice);
		}
	}
}
