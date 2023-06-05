package com.company.app.wildberries_searcher.component.data.filter;

import com.company.app.wildberries_desire_lot.component.common.data.ResponseProducts;
import com.company.app.wildberries_desire_lot.component.common.data.Size;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

class WildberriesSearcherContainsSizeFilterTest {

    private WildberriesSearcherContainsSizeFilter filter;

    @BeforeEach
    public void init() {
        filter = new WildberriesSearcherContainsSizeFilter();
    }

    @Test
    void getUserSize_positive_test() {
        ResponseProducts responseProducts = createResponseProducts();

        Optional<Size> userSize = filter.getUserSize(responseProducts, "50;52");

        Assertions.assertTrue(userSize.isPresent());
    }

    @Test
    void getUserSize_negative_test() {
        ResponseProducts responseProducts = createResponseProducts();

        Optional<Size> userSize = filter.getUserSize(responseProducts, "52;54");

        Assertions.assertTrue(userSize.isEmpty());
    }

    private ResponseProducts createResponseProducts() {
        return ResponseProducts.builder()
                .sizes(List.of(Size.builder().name("46").build()
                        , Size.builder().name("48").build()
                        , Size.builder().name("50").build()
                ))
                .build();
    }
}