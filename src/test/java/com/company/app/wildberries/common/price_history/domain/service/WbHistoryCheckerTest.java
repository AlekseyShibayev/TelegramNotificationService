package com.company.app.wildberries.common.price_history.domain.service;

import java.util.Optional;

import com.company.app.common.Lists;
import com.company.app.common.entity_finder.model.PersistenceContext;
import com.company.app.configuration.SpringBootTestApplication;
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
            productRepository.save(product.setPrice(Lists.of(price)));
        });

        Optional<Product> first = entityFinder.findFirst(new PersistenceContext<>(Product.class)
            .setSpecification(ProductSpecification.articleIs("123"))
            .with(Product_.PRICE));
        Assertions.assertFalse(wbHistoryChecker.isNeedLoadPriceHistory(first.get()));
    }

    @Test
    void isNeedLoadPriceHistory_minus_7_days_test() {
        transactionTemplate.executeWithoutResult(transactionStatus -> {
            Product product = productRepository.save(new Product().setArticle("123"));
            Price price = priceRepository.save(new Price()
                .setProduct(product)
                .setCost("1"));
            productRepository.save(product.setPrice(Lists.of(price)));
        });
        moveAllToPast();
        Optional<Product> first = entityFinder.findFirst(new PersistenceContext<>(Product.class)
            .setSpecification(ProductSpecification.articleIs("123"))
            .with(Product_.PRICE));
        Assertions.assertTrue(wbHistoryChecker.isNeedLoadPriceHistory(first.get()));
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