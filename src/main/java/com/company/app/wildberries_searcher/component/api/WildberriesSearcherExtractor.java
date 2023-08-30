package com.company.app.wildberries_searcher.component.api;

import com.company.app.common.data.ResponseProducts;
import com.company.app.common.data.price_history.PriceHistory;

import java.util.List;

public interface WildberriesSearcherExtractor {

    List<ResponseProducts> extractResponseProducts(String url);

    List<PriceHistory> extractPriceHistory(String url);
}
