package com.company.app.wildberries_searcher.component;

import com.company.app.core.aop.logging.performance.PerformanceLogAnnotation;
import com.company.app.core.tool.json.JsonTool;
import com.company.app.core.tool.json.MapperSettings;
import com.company.app.core.GetRequestHandler;
import com.company.app.core.data.Response;
import com.company.app.core.data.ResponseProducts;
import com.company.app.core.data.price_history.PriceHistory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class WildberriesSearcherExtractor {

    private final GetRequestHandler getRequestHandler;
    private final JsonTool<Response> responseJsonTool;
    private final JsonTool<PriceHistory> priceHistoryJsonTool;

    @PerformanceLogAnnotation
    public List<ResponseProducts> extractResponseProducts(String url) {
        return getAllProducts(url);
    }

    public List<PriceHistory> extractPriceHistory(String url) {
        String htmlResponse = getRequestHandler.loadHtmlPage(url);
        return priceHistoryJsonTool.toJavaAsList(htmlResponse, PriceHistory.class);
    }

    private List<ResponseProducts> getAllProducts(String url) {
        List<ResponseProducts> result = new ArrayList<>();

        int i = 1;
        while (true) {
            log.debug("try to see [{}] page", i);
            String pageUrl = String.format(url, i);
            String htmlResponse = getRequestHandler.loadHtmlPage(pageUrl);
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
