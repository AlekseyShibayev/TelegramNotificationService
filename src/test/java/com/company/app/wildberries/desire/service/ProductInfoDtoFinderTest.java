package com.company.app.wildberries.desire.service;

import com.company.app.configuration.SpringBootTestApplication;
import com.company.app.wildberries.common.get_products.ProductInfoDto;
import com.company.app.wildberries.common.get_products.ProductInfoDtoFinder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

class ProductInfoDtoFinderTest extends SpringBootTestApplication {

    @Autowired
    private ProductInfoDtoFinder productInfoDtoFinder;

    @Test
    void test() {
        List<ProductInfoDto> productInfoDtoList = productInfoDtoFinder.find(List.of("161070370", "122477595"));


    }

}