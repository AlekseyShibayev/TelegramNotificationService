package com.company.app.wildberries_searcher.api;

import com.company.app.wildberries_searcher.data.WildberriesSearcherContainer;
import com.company.app.wildberries_searcher.data.WildberriesSearcherResult;

public interface WildberriesSearcherHandler {

	WildberriesSearcherResult process(WildberriesSearcherContainer wildberriesSearcherContainer);
}
