package com.company.app.wildberries.component.searcher.api;

import com.company.app.wildberries.component.common.data.ResponseProducts;
import com.company.app.wildberries.component.searcher.data.WildberriesSearcherContainer;

public interface WildberriesSearcherNotificator {

	void notify(ResponseProducts responseProducts, WildberriesSearcherContainer wildberriesSearcherContainer);
}
