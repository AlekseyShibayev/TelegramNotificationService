package com.company.app.wildberries_searcher.component.api;

import com.company.app.infrastructure.data.ResponseProducts;
import com.company.app.wildberries_searcher.component.data.WildberriesSearcherContext;

import java.util.List;

public interface WildberriesSearcherFilterer {

    List<ResponseProducts> filter(List<ResponseProducts> products, WildberriesSearcherContext wildberriesSearcherContainer);
}
