package com.company.app.wildberries.search.component.filter;

import com.company.app.wildberries.common.model.ResponseProducts;
import com.company.app.wildberries.search.component.data.WildberriesSearcherContext;

public interface WildberriesSearcherFilter {

    String getType();

    boolean isPreFilter();

    boolean doFilter(ResponseProducts responseProducts, WildberriesSearcherContext wildberriesSearcherContainer);
}
