package com.company.app.wildberries_searcher.component.data.filter;

import com.company.app.common.data.ResponseProducts;
import com.company.app.wildberries_searcher.component.data.WildberriesSearcherContext;

public interface WildberriesSearcherFilter {

    String getType();

    boolean isPreFilter();

    boolean doFilter(ResponseProducts responseProducts, WildberriesSearcherContext wildberriesSearcherContainer);
}
