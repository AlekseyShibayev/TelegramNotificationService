package com.company.app.wildberries.common.get_products;

import com.company.app.configuration.SpringBootTestApplication;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

class ProductInfoDtoFinderTest extends SpringBootTestApplication {

    @Autowired
    private ProductInfoFinder productInfoDtoFinder;

    @Test
    void success_test() {
        List<ProductInfo> list = productInfoDtoFinder.find(List.of("161070370", "122477595"));

        Map<String, ProductInfo> map = list.stream().collect(Collectors.toMap(ProductInfo::getArticle, Function.identity()));

        Assertions.assertNotNull(map.get("161070370"));
        Assertions.assertNotNull(map.get("122477595"));
    }

}