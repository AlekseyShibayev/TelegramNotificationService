package com.company.app.wildberries.component.searcher.data.filter;

import com.company.app.wildberries.component.desire_lot.data.ResponseProducts;
import com.company.app.wildberries.component.searcher.data.WildberriesSearcherContainer;

public interface WildberriesSearcherFilter {

	String getType();

	boolean isPreFilter();

	boolean doFilter(ResponseProducts responseProducts, WildberriesSearcherContainer wildberriesSearcherContainer);
}
