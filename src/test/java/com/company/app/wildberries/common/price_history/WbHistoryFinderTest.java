package com.company.app.wildberries.common.price_history;

import java.util.List;

import com.company.app.common.entity_finder.model.PersistenceContext;
import com.company.app.configuration.SeleniumConfiguration;
import com.company.app.core.util.Collections;
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
        wbHistoryFinder.findHistoryBy(Collections.list("180189153", "171785533"));

        List<Product> all = entityFinder.findAll(new PersistenceContext<>(Product.class)
            .setSpecification(ProductSpecification.articleIn(List.of("180189153")))
            .with(Product_.PRICE));

        Assertions.assertEquals(1, all.size());
        Product product = all.get(0);
        Assertions.assertEquals("180189153", product.getArticle());
        Assertions.assertTrue(Collections.isNotEmpty(product.getPrice()));
    }

}