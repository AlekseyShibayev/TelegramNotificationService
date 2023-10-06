package com.company.app.exchange_rate.scheduler.executor;

import com.company.app.core.SeleniumHtmlPageLoader;
import com.company.app.core.temp.tool.impl.DataExtractorToolImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class AliexpressExchangeRateExtractorTest {

    private static final String FILE_NAME = "exchange_rate/aliexpress_response_example.html";

    @Test
    void can_extract_aliexpress_exchangeRate_test() {
        SeleniumHtmlPageLoader seleniumHtmlPageLoader = Mockito.mock(SeleniumHtmlPageLoader.class);
        AliexpressExchangeRateExtractor aliexpressExchangeRateExtractor = new AliexpressExchangeRateExtractor(seleniumHtmlPageLoader);

        Mockito.when(seleniumHtmlPageLoader.loadHtmlPage(null))
                .thenReturn(new DataExtractorToolImpl().getFileAsString(FILE_NAME));

        String aliexpressExchangeRate = aliexpressExchangeRateExtractor.extract();

        Assertions.assertNotNull(aliexpressExchangeRate);
    }

}