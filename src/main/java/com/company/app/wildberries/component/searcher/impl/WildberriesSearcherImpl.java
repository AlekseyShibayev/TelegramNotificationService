package com.company.app.wildberries.component.searcher.impl;

import com.company.app.core.aop.logging.util.LogUtils;
import com.company.app.wildberries.component.data.ResponseProducts;
import com.company.app.wildberries.component.data.Size;
import com.company.app.wildberries.component.searcher.api.WildberriesSearcher;
import com.company.app.wildberries.component.searcher.api.WildberriesSearcherAveragePriceExtractor;
import com.company.app.wildberries.component.searcher.api.WildberriesSearcherExtractor;
import com.company.app.wildberries.component.searcher.data.WildberriesSearcherContainer;
import com.company.app.wildberries.component.searcher.util.WildberriesSearcherUrlCreator;
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

	@Autowired
	private WildberriesSearcherExtractor wildberriesSearcherExtractor;
	@Autowired
	private WildberriesSearcherAveragePriceExtractor wildberriesSearcherAveragePriceExtractor;

	@Override
	public List<WildberriesLinkDto> search(WildberriesSearcherContainer wildberriesSearcherContainer) {
		String url = WildberriesSearcherUrlCreator.createUrl(wildberriesSearcherContainer);
		List<ResponseProducts> products = wildberriesSearcherExtractor.extractResponseProducts(url);

		List<ResponseProducts> preparedProducts = products.stream()
				.filter(responseProducts -> filterOne1(responseProducts, wildberriesSearcherContainer))
				.filter(responseProducts -> filterOne2(responseProducts, wildberriesSearcherContainer))
				.collect(Collectors.toList());
		log.debug("Запускаю фильтрацию для [{}] шт.", preparedProducts.size());

		return preparedProducts.stream()
				.filter(responseProducts -> filterOne3(responseProducts, wildberriesSearcherContainer))
				.map(ResponseProducts::to)
				.distinct()
				.limit(10)
				.collect(Collectors.toList());
	}

	private boolean filterOne1(ResponseProducts responseProducts, WildberriesSearcherContainer wildberriesSearcherContainer) {
		String rating = responseProducts.getRating();
		String feedbacks = responseProducts.getFeedbacks();
		return Integer.parseInt(rating) >= 4 && Integer.parseInt(feedbacks) >= 10;
	}

	private boolean filterOne2(ResponseProducts responseProducts, WildberriesSearcherContainer wildberriesSearcherContainer) {
		Optional<Size> optional = getUserSize(responseProducts, wildberriesSearcherContainer.getDressSize());
		return optional.isPresent();
	}

	private Optional<Size> getUserSize(ResponseProducts responseProducts, String userSize) {
		List<Size> sizes = responseProducts.getSizes();
		return sizes.stream()
				.filter(size -> size.getName().equals(userSize))
				.findAny();
	}

	private boolean filterOne3(ResponseProducts responseProducts, WildberriesSearcherContainer wildberriesSearcherContainer) {
		try {
			BigDecimal averagePrice = new BigDecimal(wildberriesSearcherAveragePriceExtractor.getAveragePrice(responseProducts));
			BigDecimal currentPrice = new BigDecimal(responseProducts.getSalePriceU());
//		averagePrice = averagePrice.multiply(new BigDecimal("1.20"));
			log.debug("Цена: текущая: [{}] средняя: [{}]", currentPrice, averagePrice);
			int i = currentPrice.compareTo(averagePrice);
			return i < 0;
		} catch (Exception exception) {
			LogUtils.doExceptionLog(exception, String.format("Проблема с %s", responseProducts.getId()));
			return false;
		}
	}
}
