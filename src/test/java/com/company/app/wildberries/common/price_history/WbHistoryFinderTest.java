package com.company.app.wildberries.common.price_history;

import java.util.Optional;
import java.util.List;

import com.company.app.common.ArrayLists;
import com.company.app.common.entity_finder.model.PersistenceContext;
import com.company.app.configuration.SeleniumConfiguration;
import com.company.app.core.util.Collections;
import com.company.app.wildberries.common.price_history.domain.entity.Price;
import com.company.app.wildberries.common.price_history.domain.entity.Product;
import com.company.app.wildberries.common.price_history.domain.entity.Product_;
import com.company.app.wildberries.common.price_history.domain.specification.ProductSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


class WbHistoryFinderTest extends SeleniumConfiguration {

    @Autowired
    private WbHistoryFinder wbHistoryFinder;

    @Test
    void can_extract_price_history_from_web() {
        wbHistoryFinder.findHistoryBy(ArrayLists.create("180189153", "171785533"));

        List<Product> all = entityFinder.findAll(new PersistenceContext<>(Product.class)
                .setSpecification(ProductSpecification.articleIn(List.of("180189153")))
            .with(Product_.PRICE));

        Assertions.assertEquals(1, all.size());
        Product product = all.get(0);
        Assertions.assertEquals("180189153", product.getArticle());
        Assertions.assertTrue(Collections.isNotEmpty(product.getPrice()));
    }

    @Test
    void isNeedLoadPriceHistory_now_test() {
        transactionTemplate.executeWithoutResult(transactionStatus -> {
            Product product = productRepository.save(new Product().setArticle("123"));
            Price price = priceRepository.save(new Price()
                .setProduct(product)
                .setCost("1"));
            productRepository.save(product.setPrice(ArrayLists.create(price)));
        });

        Optional<Product> first = entityFinder.findFirst(new PersistenceContext<>(Product.class)
            .setSpecification(ProductSpecification.articleIs("123"))
            .with(Product_.PRICE));
        Assertions.assertFalse(wbHistoryFinder.isNeedLoadPriceHistory(first.get()));
    }

    @Test
    void isNeedLoadPriceHistory_minus_7_days_test() {
        transactionTemplate.executeWithoutResult(transactionStatus -> {
            Product product = productRepository.save(new Product().setArticle("123"));
            Price price = priceRepository.save(new Price()
                .setProduct(product)
                .setCost("1"));
            productRepository.save(product.setPrice(ArrayLists.create(price)));
        });
        moveAllToPast();
        Optional<Product> first = entityFinder.findFirst(new PersistenceContext<>(Product.class)
            .setSpecification(ProductSpecification.articleIs("123"))
            .with(Product_.PRICE));
        Assertions.assertTrue(wbHistoryFinder.isNeedLoadPriceHistory(first.get()));
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
        Assertions.assertTrue(wbHistoryFinder.isNeedLoadPriceHistory(product));
    }

}