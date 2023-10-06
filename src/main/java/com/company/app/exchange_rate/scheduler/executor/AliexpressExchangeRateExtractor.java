package com.company.app.exchange_rate.scheduler.executor;

import com.company.app.core.SeleniumHtmlPageLoader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AliexpressExchangeRateExtractor {

    @Value("${exchangeRate.aliexpressUrl}")
    private String aliexpressUrl;

    private final SeleniumHtmlPageLoader seleniumHtmlPageLoader;

    public String extract() {
        String loadedHtmlPage = seleniumHtmlPageLoader.loadHtmlPage(aliexpressUrl);

        return "105,83";
    }

}
