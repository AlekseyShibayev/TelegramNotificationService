package com.company.app.wildberries_searcher.component.data.filter;

import com.company.app.wildberries_desire_lot.component.common.data.ResponseProducts;
import com.company.app.wildberries_searcher.component.data.WildberriesSearcherContainer;

public interface WildberriesSearcherFilter {

	String getType();

	boolean isPreFilter();

	boolean doFilter(ResponseProducts responseProducts, WildberriesSearcherContainer wildberriesSearcherContainer);
}
