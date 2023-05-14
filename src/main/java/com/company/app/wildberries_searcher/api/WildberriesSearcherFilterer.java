package com.company.app.wildberries_searcher.api;

import com.company.app.wildberries_desire_lot.component.data.ResponseProducts;
import com.company.app.wildberries_searcher.data.WildberriesSearcherContainer;

import java.util.List;

public interface WildberriesSearcherFilterer {

	List<ResponseProducts> filter(List<ResponseProducts> products, WildberriesSearcherContainer wildberriesSearcherContainer);
}
