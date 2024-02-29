package com.company.app.wildberries.common.price_history;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.company.app.common.entity_finder.EntityFinder;
import com.company.app.common.entity_finder.model.PersistenceContext;
import com.company.app.common.selenium.SeleniumService;
import com.company.app.common.selenium.model.Request;
import com.company.app.common.selenium.model.Response;
import com.company.app.common.tool.CaptchaFighter;
import com.company.app.common.tool.HttpService;
import com.company.app.common.tool.json.JsonMapper;
import com.company.app.common.tool.json.MapperSettings;
import com.company.app.core.util.Collections;
import com.company.app.core.util.Strings;
import com.company.app.wildberries.common.price_history.domain.entity.Price;
import com.company.app.wildberries.common.price_history.domain.entity.Product_;
import com.company.app.wildberries.common.price_history.domain.repository.ProductRepository;
import com.company.app.wildberries.common.price_history.domain.specification.ProductSpecification;
import com.company.app.wildberries.common.util.WildberriesUrlCreator;
import com.company.app.wildberries.common.price_history.domain.entity.Product;
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
    private final ProductRepository productRepository;
    private final HttpService httpService;
    private final EntityFinder entityFinder;
    private final CaptchaFighter captchaFighter;

    public List<Product> findHistoryBy(Collection<String> articleList) {
        List<Product> all = entityFinder.findAll(new PersistenceContext<>(Product.class)
            .setSpecification(ProductSpecification.articleIn(articleList))
            .with(Product_.PRICE));

        Map<String, Product> articleVsProduct = all.stream()
            .collect(Collectors.toMap(Product::getArticle, Function.identity()));

        List<Request> requests = articleList.stream()
            .filter(article -> isNeedLoadPriceHistoryUrl(articleVsProduct, article))
            .map(WbHistoryFinder::mapArticleToRequest)
            .collect(Collectors.toCollection(ArrayList::new));

        List<Response> responseList = seleniumService.findByWeb(requests);

        List<Product> newProducts = responseList.stream()
            .map(response -> productRepository.save(mapResponseToProduct(response)))
            .collect(Collectors.toCollection(ArrayList::new));

        List<Product> needLoadPriceHistory = all.stream().filter(WbHistoryFinder::isNeedLoadPriceHistory).collect(Collectors.toList());
        needLoadPriceHistory.addAll(newProducts);

        for (Product product : needLoadPriceHistory) {
            httpService.get(product.getHistoryPriceUrl())
                .ifPresent(json -> addPriceToProduct(product, json));
            captchaFighter.fight(1500, 5000);
        }

        all.addAll(newProducts);
        return all;
    }

    private static boolean isNeedLoadPriceHistory(Product product) {
        List<Price> price = product.getPrice();
        if (Collections.isEmpty(price)) { // TODO: add check for amount and update time
            return true;
        } else {
            return false;
        }
    }

    private static Request mapArticleToRequest(String article) {
        return new Request()
            .setEntityView(article)
            .setPartOfUrl(PART_OF_URL)
            .setUrl(WildberriesUrlCreator.getUrlForResponse(article));
    }

    private static Product mapResponseToProduct(Response response) {
        Request request = response.getRequest();
        String historyPriceUrl = response.getFullUrl();
        return new Product()
            .setArticle(request.getEntityView())
            .setHistoryPriceUrl(historyPriceUrl);
    }

    private static boolean isNeedLoadPriceHistoryUrl(Map<String, Product> articleVsProduct, String article) {
        if (articleVsProduct.containsKey(article) && StringUtils.isNotEmpty(articleVsProduct.get(article).getHistoryPriceUrl())) {
            return false;
        } else {
            return true;
        }
    }

    private void addPriceToProduct(Product product, String json) {
        List<VmPriceHistory> vmPriceHistories = mapJsonToJava(json);
        List<Price> priceList = vmPriceHistories.stream()
            .map(VmPriceHistory::getPrice)
            .map(vmPrice -> new Price()
                .setCost(Strings.cutEnd(vmPrice.getRub(), 2))
                .setProduct(product)
            )
            .collect(Collectors.toList());

        if (Collections.isNotEmpty(priceList)) {
            product.setPrice(priceList);
            productRepository.save(product);
        }
    }

    private List<VmPriceHistory> mapJsonToJava(String json) {
        return priceHistoryJsonTool.toJavaAsList(json,VmPriceHistory.class,
            new MapperSettings().setFailOnUnknownProperties(false));
    }

}