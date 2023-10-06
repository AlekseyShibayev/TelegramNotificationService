package com.company.app.exchange_rate.component.aliexpress;

import com.company.app.core.SpringBootTestApplicationContext;
import com.company.app.exchange_rate.scheduler.executor.AliexpressExchangeRateExtractor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class AliexpressExchangeRateExtractorTest extends SpringBootTestApplicationContext {

    @Autowired
    private AliexpressExchangeRateExtractor aliexpressExchangeRateExtractor;

    @Test
    void can_extract_aliexpress_exchangeRate_test() {
        String aliexpressExchangeRate = aliexpressExchangeRateExtractor.extract();

        Assertions.assertNotNull(aliexpressExchangeRate);
    }

}