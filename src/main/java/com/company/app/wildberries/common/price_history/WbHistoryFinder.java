package com.company.app.wildberries.common.price_history;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.company.app.common.selenium.SeleniumService;
import com.company.app.common.selenium.model.Response;
import com.company.app.common.tool.json.JsonMapper;
import com.company.app.common.tool.json.MapperSettings;
import com.company.app.wildberries.common.util.WildberriesUrlCreator;
import com.company.app.wildberries.common.price_history.domain.entity.Product;
import com.company.app.wildberries.common.price_history.domain.service.ProductCreator;
import com.company.app.wildberries.common.price_history.model.VmPriceHistory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class WbHistoryFinder {

    private static final String PART_OF_URL = "price-history.json";

    private final SeleniumService seleniumService;
    private final JsonMapper<VmPriceHistory> priceHistoryJsonTool;
    private final ProductCreator productCreator;

    public List<Product> findHistoryBy(Collection<String> articleList) {
        var urlVsArticle = articleList.stream()
                .collect(Collectors.toMap(WildberriesUrlCreator::getUrlForResponse, article -> article));

        var responseList = seleniumService.findByWeb(urlVsArticle.keySet(), PART_OF_URL);
        var responseWithBodyList = responseList.stream().filter(response -> StringUtils.isNotEmpty(response.getBody())).collect(Collectors.toList());

        log.debug("finish load from web [{}] articles, find [{}], can not load [{}]",
                articleList.size(), responseWithBodyList.size(), articleList.size() - responseWithBodyList.size());

        return responseWithBodyList.stream()
                .map(response -> createProduct(urlVsArticle, response))
                .collect(Collectors.toList());
    }

    private Product createProduct(Map<String, String> urlVsArticle, Response response) {
        List<VmPriceHistory> vmPriceHistoryList = mapJsonToJava(response);
        String urlBefore = response.getUrlBefore();
        String article = urlVsArticle.get(urlBefore);
        return productCreator.create(article, vmPriceHistoryList);
    }

    private List<VmPriceHistory> mapJsonToJava(Response response) {
        return priceHistoryJsonTool.toJavaAsList(response.getBody(),
            VmPriceHistory.class,
            new MapperSettings().setFailOnUnknownProperties(false));
    }

}