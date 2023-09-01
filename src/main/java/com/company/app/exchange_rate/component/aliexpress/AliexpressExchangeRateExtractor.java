package com.company.app.exchange_rate.component.aliexpress;

import com.company.app.core.GetRequestHandler;
import com.company.app.core.SeleniumHtmlPageLoader;
import com.company.app.core.tool.api.DataExtractorTool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AliexpressExchangeRateExtractor {

    @Value("${exchangeRate.aliexpressUrl}")
    private String aliexpressUrl;

    private final AliexpressReceiver aliexpressReceiver;
    private final DataExtractorTool dataExtractorTool;
    private final SeleniumHtmlPageLoader seleniumHtmlPageLoader;
    private final GetRequestHandler getRequestHandler;

    public String extract() {
        String htmlPage = seleniumHtmlPageLoader.loadHtmlPage(aliexpressUrl);
//        String htmlPage = getRequestHandler.loadHtmlPage(aliexpressUrl);



        return getExchangeRate(htmlPage);
    }

    String getExchangeRate(String htmlPage) {
        JSONObject jsonObject = new JSONObject(htmlPage);
        return dataExtractorTool.getFirstString(jsonObject, "finalPrice");
    }

}
