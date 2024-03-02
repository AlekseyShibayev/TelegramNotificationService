package com.company.app.wildberries.search.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.company.app.common.tool.CaptchaFighter;
import com.company.app.common.tool.HttpService;
import com.company.app.common.tool.json.JsonMapper;
import com.company.app.common.tool.json.MapperSettings;
import com.company.app.core.aop.logging.performance.PerformanceLogAnnotation;
import com.company.app.wildberries.common.model.VmProduct;
import com.company.app.wildberries.common.model.VmResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class WbSearcherApi {

    private final HttpService getRequestHandler;
    private final CaptchaFighter captchaFighter;
    private final JsonMapper<VmResponse> responseJsonTool;

    @PerformanceLogAnnotation
    public List<VmProduct> findProductsByWeb(String url) {
        List<VmProduct> result = new ArrayList<>();

        int i = 1;
        while (true) {
            log.debug("try to see [{}] page", i);
            String pageUrl = String.format(url, i);
            List<VmProduct> products = findOnePage(pageUrl);
            captchaFighter.fight(1500, 5000);
            if (products.isEmpty()) {
                log.debug("В ходе поиска было [{}] запросов к ВБ. Найдено [{}] товаров.", i, result.size());
                return result;
            } else {
                result.addAll(products);
                i++;
            }
        }
    }

    private List<VmProduct> findOnePage(String pageUrl) {
        Optional<String> htmlResponse = getRequestHandler.get(pageUrl);

        VmResponse response = responseJsonTool.toJavaAsObject(htmlResponse.get(),
            VmResponse.class,
            new MapperSettings().setFailOnUnknownProperties(false));

        return response.getData().getProducts();
    }

}