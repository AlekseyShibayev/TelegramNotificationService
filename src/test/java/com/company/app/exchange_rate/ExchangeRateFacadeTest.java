package com.company.app.exchange_rate;

import com.company.app.core.SpringBootTestApplicationContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class ExchangeRateFacadeTest extends SpringBootTestApplicationContext {

    @Autowired
    private ExchangeRateFacade exchangeRateFacade;

    @Test
    void test() {

        exchangeRateFacade.extract();

    }

}