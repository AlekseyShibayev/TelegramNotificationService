package com.company.app.wildberries.common.price_history.domain.service;

import java.util.Optional;

import com.company.app.configuration.SpringBootTestApplication;
import com.company.app.core.util.Collections;
import com.company.app.infrastructure.jpa.entityfinder.model.CommonQuery;
import com.company.app.wildberries.common.price_history.domain.entity.Price;
import com.company.app.wildberries.common.price_history.domain.entity.Product;
import com.company.app.wildberries.common.price_history.domain.entity.Product_;
import com.company.app.wildberries.common.price_history.domain.specification.ProductSpecification;
import com.company.app.wildberries.common.price_history.service.WbHistoryChecker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


class WbHistoryCheckerTest extends SpringBootTestApplication {

    @Autowired
    private WbHistoryChecker wbHistoryChecker;

    @Test
    void isNeedLoadPriceHistory_now_test() {
        transactionTemplate.executeWithoutResult(transactionStatus -> {
            Product product = productRepository.save(new Product().setArticle("123"));
            Price price = priceRepository.save(new Price()
                .setProduct(product)
                .setCost("1"));
            productRepository.save(product.setPrice(Collections.list(price)));
        });

        Product first = entityFinder.findAllAsList(new CommonQuery<>(Product.class)
            .setSpecification(ProductSpecification.articleIs("123"))
            .with(Product_.PRICE)).get(0);
        Assertions.assertFalse(wbHistoryChecker.isNeedLoadPriceHistory(first));
    }

    @Test
    void isNeedLoadPriceHistory_minus_7_days_test() {
        transactionTemplate.executeWithoutResult(transactionStatus -> {
            Product product = productRepository.save(new Product().setArticle("123"));
            Price price = priceRepository.save(new Price()
                .setProduct(product)
                .setCost("1"));
            productRepository.save(product.setPrice(Collections.list(price)));
        });
        moveAllToPast();
        Product first = entityFinder.findAllAsList(new CommonQuery<>(Product.class)
            .setSpecification(ProductSpecification.articleIs("123"))
            .with(Product_.PRICE)).get(0);
        Assertions.assertTrue(wbHistoryChecker.isNeedLoadPriceHistory(first));
    }

    private void moveAllToPast() {
        transactionTemplate.executeWithoutResult(transactionStatus -> {
            entityManager.createNativeQuery("update PRICE set CREATE_DATE = (NOW() - interval '7 day');")
                .executeUpdate();
        });
    }

    @Test
    void isNeedLoadPriceHistory_emptyList_test() {
        Product product = productRepository.save(new Product().setArticle("123"));
        Assertions.assertTrue(wbHistoryChecker.isNeedLoadPriceHistory(product));
    }

}