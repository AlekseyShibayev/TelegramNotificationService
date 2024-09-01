package com.company.app.wildberries.common.util;

import lombok.experimental.UtilityClass;


@UtilityClass
public class WildberriesUrlCreator {

    private static final String PRODUCT_URL = "https://www.wildberries.ru/catalog/%s/detail.aspx";

    public static String getProductUrl(String article) {
        return PRODUCT_URL.formatted(article);
    }

}
