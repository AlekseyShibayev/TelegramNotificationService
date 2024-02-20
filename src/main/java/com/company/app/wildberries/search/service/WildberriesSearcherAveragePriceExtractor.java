package com.company.app.wildberries.search.service;

import java.util.List;
import java.util.OptionalDouble;

import com.company.app.wildberries.common.model.PriceHistory;
import com.company.app.wildberries.common.model.ResponseProducts;
import com.company.app.wildberries.search.service.data.util.PriceHistoryUrlCreator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class WildberriesSearcherAveragePriceExtractor {

    private final WildberriesSearcherExtractor wildberriesSearcherExtractor;

    public int getAveragePrice(ResponseProducts responseProducts) {
        String url = PriceHistoryUrlCreator.createUrl(String.valueOf(responseProducts.getId()));
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
