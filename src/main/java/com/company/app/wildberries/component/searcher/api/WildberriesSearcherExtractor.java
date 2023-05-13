package com.company.app.wildberries.component.searcher.api;

import com.company.app.wildberries.component.data.ResponseProducts;
import com.company.app.wildberries.component.data.price_history.PriceHistory;

import java.util.List;

public interface WildberriesSearcherExtractor {

	List<ResponseProducts> extractResponseProducts(String url);

	List<PriceHistory> extractPriceHistory(String url);
}
