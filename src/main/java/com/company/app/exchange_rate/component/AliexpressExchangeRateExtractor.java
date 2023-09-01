package com.company.app.exchange_rate.component;

import com.company.app.core.tool.api.DataExtractorTool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AliexpressExchangeRateExtractor {

    private final AliexpressReceiver aliexpressReceiver;
    private final DataExtractorTool dataExtractorTool;

    public String extract() {
        String htmlResponse = aliexpressReceiver.getHtmlResponse();
        return getExchangeRate(htmlResponse);
    }

    String getExchangeRate(String response) {
        JSONObject jsonObject = new JSONObject(response);
        return dataExtractorTool.getFirstString(jsonObject, "finalPrice");
    }

}
