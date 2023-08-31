package com.company.app.wildberries_searcher.component.filter;

import com.company.app.infrastructure.data.ResponseProducts;
import com.company.app.wildberries_searcher.component.data.WildberriesSearcherContext;

public interface WildberriesSearcherFilter {

    String getType();

    boolean isPreFilter();

    boolean doFilter(ResponseProducts responseProducts, WildberriesSearcherContext wildberriesSearcherContainer);
}
