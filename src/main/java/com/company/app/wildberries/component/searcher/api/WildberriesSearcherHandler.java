package com.company.app.wildberries.component.searcher.api;

import com.company.app.wildberries.component.searcher.data.WildberriesSearcherContainer;
import com.company.app.wildberries.component.searcher.data.WildberriesSearcherResult;

public interface WildberriesSearcherHandler {

	WildberriesSearcherResult process(WildberriesSearcherContainer wildberriesSearcherContainer);
}
