package com.company.app.wildberries.component.searcher.api;

import com.company.app.wildberries.component.common.data.ResponseProducts;
import com.company.app.wildberries.component.searcher.data.WildberriesSearcherContainer;

import java.util.List;

public interface WildberriesSearcherFilterer {

	List<ResponseProducts> filter(List<ResponseProducts> products, WildberriesSearcherContainer wildberriesSearcherContainer);
}
