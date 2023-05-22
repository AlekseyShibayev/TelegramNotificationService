package com.company.app.wildberries_searcher.component.api;

import com.company.app.wildberries_searcher.component.data.WildberriesSearcherContainer;
import com.company.app.wildberries_searcher.component.data.WildberriesSearcherResult;

public interface WildberriesSearcherHandler {

	WildberriesSearcherResult process(WildberriesSearcherContainer wildberriesSearcherContainer);
}
