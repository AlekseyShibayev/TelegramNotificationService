package com.company.app.wildberries.search.service.filter;

import com.company.app.wildberries.common.model.VmProduct;
import com.company.app.wildberries.search.model.WbSearchContext;
import org.springframework.stereotype.Service;


@Service
public class MaxPriceWbFilter implements WbFilter {

    private static final int MAX_PRICE = 5000_00;

    @Override
    public boolean test(VmProduct product, WbSearchContext context) {
        Integer price = product.getSalePriceU();
        return price <= MAX_PRICE;
    }

}