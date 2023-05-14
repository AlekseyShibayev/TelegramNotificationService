package com.company.app.wildberries_searcher.impl;

import com.company.app.wildberries_searcher.util.WildberriesSearcherPriceHistoryUrlCreator;
import com.company.app.wildberries_desire_lot.component.data.ResponseProducts;
import com.company.app.wildberries_desire_lot.component.data.price_history.PriceHistory;
import com.company.app.wildberries_searcher.api.WildberriesSearcherAveragePriceExtractor;
import com.company.app.wildberries_searcher.api.WildberriesSearcherExtractor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.OptionalDouble;

@Slf4j
@Component
public class WildberriesSearcherAveragePriceExtractorImpl implements WildberriesSearcherAveragePriceExtractor {

	@Autowired
	private WildberriesSearcherExtractor wildberriesSearcherExtractor;

	@Override
	public int getAveragePrice(ResponseProducts responseProducts) {
		String url = WildberriesSearcherPriceHistoryUrlCreator.createUrl(String.valueOf(responseProducts.getId()));
		List<PriceHistory> priceHistory = wildberriesSearcherExtractor.extractPriceHistory(url);
		OptionalDouble average = priceHistory.stream()
				.mapToInt(this::getRubAsInt)
				.average();
		return (int) average.orElse(0.00);
	}

	private int getRubAsInt(PriceHistory priceHistory) {
		return Integer.parseInt(priceHistory.getPrice().getRub());
	}
}
