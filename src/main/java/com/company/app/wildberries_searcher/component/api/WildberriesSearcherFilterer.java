package com.company.app.wildberries_searcher.component.api;

import com.company.app.wildberries_desire_lot.component.common.data.ResponseProducts;
import com.company.app.wildberries_searcher.component.data.WildberriesSearcherContainer;

import java.util.List;

public interface WildberriesSearcherFilterer {

    List<ResponseProducts> filter(List<ResponseProducts> products, WildberriesSearcherContainer wildberriesSearcherContainer);
}
