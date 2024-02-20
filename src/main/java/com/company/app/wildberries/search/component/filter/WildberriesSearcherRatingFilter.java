package com.company.app.wildberries.search.component.filter;

import com.company.app.wildberries.common.model.ResponseProducts;
import com.company.app.wildberries.search.component.data.WildberriesSearcherContext;
import org.springframework.stereotype.Component;


@Component
public class WildberriesSearcherRatingFilter implements WildberriesSearcherFilter {

    private static final String TYPE = "rating";

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
        return withRating(responseProducts);
    }

    private boolean withRating(ResponseProducts responseProducts) {
        String rating = responseProducts.getRating();
        return Integer.parseInt(rating) >= 4;
    }

}
