package com.company.app.exchange_rate.component.impl;

import com.company.app.core.tool.api.DataExtractorTool;
import com.company.app.exchange_rate.component.api.AliexpressExchangeRateExtractor;
import com.company.app.exchange_rate.component.api.AliexpressReceiver;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Setter
@Component
public class AliexpressExchangeRateExtractorImpl implements AliexpressExchangeRateExtractor {

    @Autowired
    private AliexpressReceiver aliexpressReceiver;
    @Autowired
    private DataExtractorTool dataExtractorTool;

    @Override
    public String extract() {
        String htmlResponse = aliexpressReceiver.getHtmlResponse();
        return getExchangeRate(htmlResponse);
    }

    String getExchangeRate(String response) {
        JSONObject jsonObject = new JSONObject(response);
        return dataExtractorTool.getFirstString(jsonObject, "finalPrice");
    }
}
