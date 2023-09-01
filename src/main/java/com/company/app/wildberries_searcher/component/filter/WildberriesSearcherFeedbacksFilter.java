package com.company.app.wildberries_searcher.component.filter;

import com.company.app.core.data.ResponseProducts;
import com.company.app.wildberries_searcher.component.data.WildberriesSearcherContext;
import org.springframework.stereotype.Component;

@Component
public class WildberriesSearcherFeedbacksFilter implements WildberriesSearcherFilter {

    private static final String TYPE = "feedbacks";

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
        return withFeedbacks(responseProducts);
    }

    private boolean withFeedbacks(ResponseProducts responseProducts) {
        String feedbacks = responseProducts.getFeedbacks();
        return Integer.parseInt(feedbacks) >= 10;
    }
}
