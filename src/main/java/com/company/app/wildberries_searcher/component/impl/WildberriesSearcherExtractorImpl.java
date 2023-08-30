package com.company.app.wildberries_searcher.component.impl;

import com.company.app.core.aop.logging.performance.PerformanceLogAnnotation;
import com.company.app.core.tool.json.JsonTool;
import com.company.app.core.tool.json.MapperSettings;
import com.company.app.infrastructure.GetRequestHandler;
import com.company.app.infrastructure.data.Response;
import com.company.app.infrastructure.data.ResponseProducts;
import com.company.app.infrastructure.data.price_history.PriceHistory;
import com.company.app.wildberries_searcher.component.api.WildberriesSearcherExtractor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class WildberriesSearcherExtractorImpl implements WildberriesSearcherExtractor {

    @Autowired
    private GetRequestHandler getRequestHandler;
    @Autowired
    private JsonTool<Response> responseJsonTool;
    @Autowired
    private JsonTool<PriceHistory> priceHistoryJsonTool;

    @PerformanceLogAnnotation
    @Override
    public List<ResponseProducts> extractResponseProducts(String url) {
        return getAllProducts(url);
    }

    @Override
    public List<PriceHistory> extractPriceHistory(String url) {
        String htmlResponse = getRequestHandler.getResponseBodyAsString(url);
        return priceHistoryJsonTool.toJavaAsList(htmlResponse, PriceHistory.class);
    }

    private List<ResponseProducts> getAllProducts(String url) {
        List<ResponseProducts> result = new ArrayList<>();

        int i = 1;
        while (true) {
            log.debug("try to see [{}] page", i);
            String pageUrl = String.format(url, i);
            String htmlResponse = getRequestHandler.getResponseBodyAsString(pageUrl);
            Response response = responseJsonTool.toJavaAsObject(htmlResponse, Response.class, MapperSettings.builder()
                    .failOnUnknownProperties(false)
                    .build());
            List<ResponseProducts> products = response.getData().getProducts();
            if (products.isEmpty()) {
                log.debug("В ходе поиска было [{}] запросов к ВБ.", i);
                return result;
            } else {
                result.addAll(products);
                i++;
            }
        }
    }

}
