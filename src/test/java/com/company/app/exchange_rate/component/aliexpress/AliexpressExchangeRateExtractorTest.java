package com.company.app.exchange_rate.component.aliexpress;

import com.company.app.core.SpringBootTestApplicationContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class AliexpressExchangeRateExtractorTest extends SpringBootTestApplicationContext {

    @Autowired
    private AliexpressExchangeRateExtractor aliexpressExchangeRateExtractor;

    @Test
    void test() {
        String aliexpressExchangeRate = aliexpressExchangeRateExtractor.extract();

        Assertions.assertNotNull(aliexpressExchangeRate);
    }

}