package com.company.app.wildberries_searcher.component.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UtilityClass
public class PriceHistoryUrlCreator {

    private static final String WILDBERRIES_PRICE_HISTORY_URL = "https://wbx-content-v2.wbstatic.net/price-history/%s.json";

    public static String createUrl(String article) {
        String format = String.format(WILDBERRIES_PRICE_HISTORY_URL, article);
        log.debug("price history url:\n[{}]", format);
        return format;
    }

}
