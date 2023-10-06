package com.company.app.exchange_rate.scheduler.executor;

import com.company.app.core.SeleniumHtmlPageLoader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class AliexpressExchangeRateExtractor {

    @Value("${exchangeRate.aliexpressUrl}")
    private String aliexpressUrl;

    private final SeleniumHtmlPageLoader seleniumHtmlPageLoader;

    public String extract() {
        String result = "";

        String loadedHtmlPage = seleniumHtmlPageLoader.loadHtmlPage(aliexpressUrl);
        Optional<Element> optional = Jsoup.parse(loadedHtmlPage)
                .getElementsByTag("div")
                .stream()
                .filter(element -> element.html().contains("'itemId': '1005005201823586','price': '"))
                .findFirst();
        if (optional.isPresent()) {
            String html = optional.get().html();
            String[] split = html.split("'itemId': '1005005201823586','price': '");
            String secondPart = split[1];
            String[] split1 = secondPart.split(" ₽','currency': 'RUB'");
            result = split1[0];
        }

        return result;
    }

}
