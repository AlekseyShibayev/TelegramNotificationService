package com.company.app.core.wildberries_common;

import lombok.experimental.UtilityClass;

@UtilityClass
public class WildberriesUrlCreator {

    //    static final String URL_BONE_0 = "https://card.wb.ru/cards/detail?spp=0&regions=80,38,4,83,33,68,30,86,40,1,22,31,48,110&pricemarginCoeff=1.0&reg=0&appType=1&emp=0&locale=ru&lang=ru&curr=rub&couponsGeo=3,6,19,21,8&dest=-3827446&nm=";
    static final String URL_BONE = "https://card.wb.ru/cards/detail?appType=1&curr=rub&dest=-1257786&regions=80,38,83,4,64,33,68,70,30,40,86,75,69,1,66,110,22,48,31,71,114&spp=31&nm=";
    static final String URL_RESPONSE = "https://www.wildberries.ru/catalog/%s/detail.aspx";

    public static String getUrlForPriceSearch(String id) {
        return URL_BONE + id;
    }

    public static String getUrlForResponse(String id) {
        return String.format(URL_RESPONSE, id);
    }

}
