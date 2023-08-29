package com.company.app.wildberries_searcher.component.api;

import com.company.app.wildberries_searcher.component.data.WildberriesSearcherContext;
import com.company.app.wildberries_searcher.component.data.WildberriesSearcherResult;

public interface WildberriesSearcherHandler {

    WildberriesSearcherResult process(WildberriesSearcherContext wildberriesSearcherContainer);
}
