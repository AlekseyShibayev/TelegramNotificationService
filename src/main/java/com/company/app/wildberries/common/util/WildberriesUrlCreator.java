package com.company.app.wildberries.common.util;

import lombok.experimental.UtilityClass;


@UtilityClass
public class WildberriesUrlCreator {

    //    static final String URL_BONE_0 = "https://card.wb.ru/cards/detail?spp=0&regions=80,38,4,83,33,68,30,86,40,1,22,31,48,110&pricemarginCoeff=1.0&reg=0&appType=1&emp=0&locale=ru&lang=ru&curr=rub&couponsGeo=3,6,19,21,8&dest=-3827446&nm=";
    static final String URL_BONE = "https://card.wb.ru/cards/v2/detail?appType=1&curr=rub&dest=12354108&spp=30&ab_testing=false&nm=";
    static final String URL_RESPONSE = "https://www.wildberries.ru/catalog/%s/detail.aspx";

    public static String getUrlForPriceSearch(String id) {
        return URL_BONE + id;
    }

    public static String getUrlForResponse(String id) {
        return String.format(URL_RESPONSE, id);
    }

}
