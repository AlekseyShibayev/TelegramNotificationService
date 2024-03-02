package com.company.app.wildberries.common.price_history;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.company.app.common.entity_finder.EntityFinder;
import com.company.app.common.entity_finder.model.PersistenceContext;
import com.company.app.common.selenium.SeleniumService;
import com.company.app.common.selenium.model.Request;
import com.company.app.common.selenium.model.Response;
import com.company.app.core.util.Collections;
import com.company.app.wildberries.common.price_history.domain.entity.Price;
import com.company.app.wildberries.common.price_history.domain.entity.Product;
import com.company.app.wildberries.common.price_history.domain.entity.Product_;
import com.company.app.wildberries.common.price_history.domain.repository.ProductRepository;
import com.company.app.wildberries.common.price_history.domain.service.WbHistoryChecker;
import com.company.app.wildberries.common.price_history.domain.service.WbHistoryLoader;
import com.company.app.wildberries.common.price_history.domain.specification.ProductSpecification;
import com.company.app.wildberries.common.util.WildberriesUrlCreator;
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
    private final ProductRepository productRepository;
    private final EntityFinder entityFinder;
    private final WbHistoryLoader wbHistoryLoader;
    private final WbHistoryChecker wbHistoryChecker;

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

        List<Product> needLoadPriceHistory = all.stream().filter(this::isNeedLoadPriceHistory).collect(Collectors.toList());
        needLoadPriceHistory.addAll(newProducts);

        wbHistoryLoader.loadPriceHistory(needLoadPriceHistory);

        all.addAll(newProducts);
        return all;
    }

    boolean isNeedLoadPriceHistory(Product product) {
        List<Price> priceList = product.getPrice();
        if (Collections.isEmpty(priceList)) {
            return true;
        } else {
            Price price = priceList.get(0);
            ZonedDateTime createDate = price.getCreateDate();
            return createDate.plusDays(3).isBefore(ZonedDateTime.now());
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

}