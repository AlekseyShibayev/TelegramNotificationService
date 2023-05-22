package com.company.app.wildberries_searcher.component.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class WildberriesSearcherPriceHistoryUrlCreator {

	private static final String WILDBERRIES_PRICE_HISTORY_URL = "https://wbx-content-v2.wbstatic.net/price-history/%s.json";

	public static String createUrl(String article) {
		return String.format(WILDBERRIES_PRICE_HISTORY_URL, article);
	}
}
