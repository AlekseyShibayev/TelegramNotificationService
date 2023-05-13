package com.company.app.wildberries.component.searcher.impl;

import com.company.app.wildberries.component.data.ResponseProducts;
import com.company.app.wildberries.component.data.price_history.PriceHistory;
import com.company.app.wildberries.component.searcher.api.WildberriesSearcherAveragePriceExtractor;
import com.company.app.wildberries.component.searcher.api.WildberriesSearcherExtractor;
import com.company.app.wildberries.component.searcher.util.WildberriesSearcherCaptchaFighter;
import com.company.app.wildberries.component.searcher.util.WildberriesSearcherPriceHistoryUrlCreator;
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
		WildberriesSearcherCaptchaFighter.fight(3_000, 10_000);
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
