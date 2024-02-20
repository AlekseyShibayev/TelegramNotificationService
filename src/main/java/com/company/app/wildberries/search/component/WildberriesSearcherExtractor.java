package com.company.app.wildberries.search.component;

import java.util.ArrayList;
import java.util.List;

import com.company.app.common.tool.GetRequestHandler;
import com.company.app.common.tool.json.JsonMapper;
import com.company.app.common.tool.json.MapperSettings;
import com.company.app.core.aop.logging.performance.PerformanceLogAnnotation;
import com.company.app.wildberries.common.model.PriceHistory;
import com.company.app.wildberries.common.model.Response;
import com.company.app.wildberries.common.model.ResponseProducts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class WildberriesSearcherExtractor {

    private final GetRequestHandler getRequestHandler;
    private final JsonMapper<Response> responseJsonTool;
    private final JsonMapper<PriceHistory> priceHistoryJsonTool;

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
            Response response = responseJsonTool.toJavaAsObject(htmlResponse,
                Response.class,
                new MapperSettings().setFailOnUnknownProperties(false));

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
