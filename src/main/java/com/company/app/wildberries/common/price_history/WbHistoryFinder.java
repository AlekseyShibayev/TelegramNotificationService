package com.company.app.wildberries.common.price_history;

import java.util.ArrayList;
import java.util.List;

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
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class WbHistoryFinder {

    private static final String PART_OF_URL = "price-history.json";

    private final SeleniumService seleniumService;
    private final JsonMapper<VmPriceHistory> priceHistoryJsonTool;
    private final ProductCreator productCreator;

    public Product findHistoryBy(String article) {
        String url = WildberriesUrlCreator.getUrlForResponse(article);
        var result = seleniumService.findByWeb(url, PART_OF_URL)
            .map(this::mapJsonToJava)
            .orElse(new ArrayList<>());

        return productCreator.create(article, result);
    }

    private List<VmPriceHistory> mapJsonToJava(Response response) {
        return priceHistoryJsonTool.toJavaAsList(response.getBody(),
            VmPriceHistory.class,
            new MapperSettings().setFailOnUnknownProperties(false));
    }

}