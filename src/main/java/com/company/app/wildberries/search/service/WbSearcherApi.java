package com.company.app.wildberries.search.service;

import com.company.app.common.tool.GetRequestHandler;
import com.company.app.common.tool.json.JsonMapper;
import com.company.app.common.tool.json.MapperSettings;
import com.company.app.core.aop.logging.performance.PerformanceLogAnnotation;
import com.company.app.wildberries.common.model.VmProduct;
import com.company.app.wildberries.common.model.VmResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class WbSearcherApi {

    private final GetRequestHandler getRequestHandler;
    private final JsonMapper<VmResponse> responseJsonTool;

    @PerformanceLogAnnotation
    public List<VmProduct> findProductsByWeb(String url) {
        List<VmProduct> result = new ArrayList<>();

        int i = 1;
        while (true) {
            log.debug("try to see [{}] page", i);
            String pageUrl = String.format(url, i);
            String htmlResponse = getRequestHandler.loadHtmlPage(pageUrl);
            VmResponse response = responseJsonTool.toJavaAsObject(htmlResponse,
                    VmResponse.class,
                    new MapperSettings().setFailOnUnknownProperties(false));

            List<VmProduct> products = response.getData().getProducts();
            if (products.isEmpty()) {
                log.debug("В ходе поиска было [{}] запросов к ВБ. Найдено [{}] товаров.", i, result.size());
                return result;
            } else {
                result.addAll(products);
                i++;
            }
        }
    }

}