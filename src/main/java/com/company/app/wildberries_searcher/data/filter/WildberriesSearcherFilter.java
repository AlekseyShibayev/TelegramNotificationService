package com.company.app.wildberries_searcher.data.filter;

import com.company.app.wildberries_desire_lot.component.data.ResponseProducts;
import com.company.app.wildberries_searcher.data.WildberriesSearcherContainer;

public interface WildberriesSearcherFilter {

	String getType();

	boolean isPreFilter();

	boolean doFilter(ResponseProducts responseProducts, WildberriesSearcherContainer wildberriesSearcherContainer);
}
