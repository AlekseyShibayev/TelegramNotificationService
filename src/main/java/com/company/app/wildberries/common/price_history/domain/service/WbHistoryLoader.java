package com.company.app.wildberries.common.price_history.domain.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.company.app.common.tool.CaptchaFighter;
import com.company.app.common.tool.HttpService;
import com.company.app.common.tool.json.JsonMapper;
import com.company.app.common.tool.json.MapperSettings;
import com.company.app.core.util.Collections;
import com.company.app.core.util.Strings;
import com.company.app.wildberries.common.price_history.domain.entity.Price;
import com.company.app.wildberries.common.price_history.domain.entity.Product;
import com.company.app.wildberries.common.price_history.domain.repository.PriceRepository;
import com.company.app.wildberries.common.price_history.domain.repository.ProductRepository;
import com.company.app.wildberries.common.price_history.model.VmPriceHistory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class WbHistoryLoader {

    private final JsonMapper<VmPriceHistory> priceHistoryJsonTool;
    private final ProductRepository productRepository;
    private final PriceRepository priceRepository;
    private final HttpService httpService;
    private final CaptchaFighter captchaFighter;

    public void loadPriceHistory(List<Product> needLoadPriceHistory) {
        int counter = 1;
        for (Product product : needLoadPriceHistory) {
            log.debug("try to load history [{}]/[{}], for article [{}]", counter, needLoadPriceHistory.size(), product.getArticle());
            httpService.get(product.getHistoryPriceUrl())
                .ifPresent(json -> addPriceToProduct(product, json));
            captchaFighter.fight(1500, 5000);
        }
    }

    private void addPriceToProduct(Product product, String json) {
        List<VmPriceHistory> vmPriceHistories = mapJsonToJava(json);
        List<Price> newPriceList = vmPriceHistories.stream()
            .map(vmPriceHistory -> mapToPrice(product, vmPriceHistory))
            .collect(Collectors.toCollection(ArrayList::new));

        if (Collections.isNotEmpty(newPriceList)) {
            priceRepository.deleteAll(product.getPrice());
            priceRepository.saveAll(newPriceList);
            product.setPrice(newPriceList);
            productRepository.save(product);
        }
    }

    private Price mapToPrice(Product product, VmPriceHistory vmPriceHistory) {
        return new Price()
            .setCost(Strings.cutEnd(vmPriceHistory.getPrice().getRub(), 2))
            .setProduct(product);
    }

    private List<VmPriceHistory> mapJsonToJava(String json) {
        return priceHistoryJsonTool.toJavaAsList(json,VmPriceHistory.class,
            new MapperSettings().setFailOnUnknownProperties(false));
    }

}