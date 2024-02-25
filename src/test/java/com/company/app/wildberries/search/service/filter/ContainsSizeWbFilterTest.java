package com.company.app.wildberries.search.service.filter;

import com.company.app.wildberries.common.model.VmProduct;
import com.company.app.wildberries.common.model.VmSize;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

class ContainsSizeWbFilterTest {

    private ContainsSizeWbFilter filter = new ContainsSizeWbFilter();

    @Test
    void getUserSize_positive_test() {
        VmProduct responseProducts = createResponseProducts();

        Optional<VmSize> userSize = filter.getUserSize(responseProducts, "50;52");

        Assertions.assertTrue(userSize.isPresent());
    }

    @Test
    void getUserSize_negative_test() {
        VmProduct responseProducts = createResponseProducts();

        Optional<VmSize> userSize = filter.getUserSize(responseProducts, "52;54");

        Assertions.assertTrue(userSize.isEmpty());
    }

    private VmProduct createResponseProducts() {
        return new VmProduct()
                .setSizes(List.of(new VmSize().setName("46")
                        , new VmSize().setName("48")
                        , new VmSize().setName("50")
                ));
    }

}