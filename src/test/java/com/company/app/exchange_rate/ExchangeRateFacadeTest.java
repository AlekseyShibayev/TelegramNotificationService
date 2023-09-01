package com.company.app.exchange_rate;

import com.company.app.core.SeleniumHtmlPageLoader;
import com.company.app.core.SpringBootTestApplicationContext;
import com.company.app.core.tool.Tool;
import com.company.app.exchange_rate.domain.entity.ExchangeRate;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

class ExchangeRateFacadeTest extends SpringBootTestApplicationContext {

    @Autowired
    private ExchangeRateFacade exchangeRateFacade;
    @MockBean
    private SeleniumHtmlPageLoader seleniumHtmlPageLoader;

    @Test
    void test() {
        Mockito.when(seleniumHtmlPageLoader.loadHtmlPage(Mockito.anyString()))
//                .thenReturn(Tool.getFileAsString("src/test/resources/exchange_rate/aliexpress_example_page.html"));
                .thenReturn(Tool.getFileAsString("exchange_rate/aliexpress_example_page.html"));

        ExchangeRate extract = exchangeRateFacade.extract();

    }

}