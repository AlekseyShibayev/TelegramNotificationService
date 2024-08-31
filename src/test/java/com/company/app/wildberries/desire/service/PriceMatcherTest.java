package com.company.app.wildberries.desire.service;

import com.company.app.wildberries.desire.PriceMatcher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class PriceMatcherTest {

    @Test
    void le_test() {
        Assertions.assertTrue(PriceMatcher.le(new BigDecimal("1"), new BigDecimal("2")));
        Assertions.assertTrue(PriceMatcher.le(new BigDecimal("1"), new BigDecimal("1")));
        Assertions.assertFalse(PriceMatcher.le(new BigDecimal("2"), new BigDecimal("1")));
    }

}