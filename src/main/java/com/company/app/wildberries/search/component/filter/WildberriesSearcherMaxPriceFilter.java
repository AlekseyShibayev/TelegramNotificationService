package com.company.app.wildberries.search.component.filter;

import com.company.app.wildberries.common.model.ResponseProducts;
import com.company.app.wildberries.search.component.data.WildberriesSearcherContext;
import org.springframework.stereotype.Component;

@Component
public class WildberriesSearcherMaxPriceFilter implements WildberriesSearcherFilter {

    private static final String TYPE = "maxPrice";
    private static final int MAX_PRICE = 5000_00;

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public boolean isPreFilter() {
        return true;
    }

    @Override
    public boolean doFilter(ResponseProducts responseProducts, WildberriesSearcherContext wildberriesSearcherContainer) {
        return withMaxPrice(responseProducts);
    }

    private boolean withMaxPrice(ResponseProducts responseProducts) {
        Integer price = responseProducts.getSalePriceU();
        return price <= MAX_PRICE;
    }
}
