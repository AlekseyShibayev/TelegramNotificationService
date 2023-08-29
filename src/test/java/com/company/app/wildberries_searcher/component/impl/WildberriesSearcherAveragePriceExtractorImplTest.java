package com.company.app.wildberries_searcher.component.impl;

import com.company.app.springboottest.application.SpringBootTestApplicationContext;
import com.company.app.wildberries_desire_lot.component.common.data.ResponseProducts;
import com.company.app.wildberries_searcher.component.api.WildberriesSearcherAveragePriceExtractor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class WildberriesSearcherAveragePriceExtractorImplTest extends SpringBootTestApplicationContext {

    @Autowired
    private WildberriesSearcherAveragePriceExtractor averagePriceExtractor;


    @Test
    void test_1() {
        ResponseProducts responseProducts = new ResponseProducts()
                .setId(152219056);

        int averagePrice = averagePriceExtractor.getAveragePrice(responseProducts);
    }

}