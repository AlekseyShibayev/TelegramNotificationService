package com.company.app.wildberries.desire;

import lombok.experimental.UtilityClass;

import java.math.BigDecimal;

@UtilityClass
public class PriceMatcher {

    /**
     * is first argument lesser or equals second argument
     */
    public static boolean le(BigDecimal desirePrice, BigDecimal realPrice) {
        int i = desirePrice.compareTo(realPrice);
        return i <= 0;
    }

}
