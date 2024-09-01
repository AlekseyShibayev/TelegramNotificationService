package com.company.app.wildberries.common.util;

import lombok.experimental.UtilityClass;

import java.math.BigDecimal;

@UtilityClass
public class PriceMatcher {

    /**
     * Is first argument lesser or equals then second argument
     */
    public static boolean le(BigDecimal first, BigDecimal second) {
        int i = first.compareTo(second);
        return i <= 0;
    }

}
