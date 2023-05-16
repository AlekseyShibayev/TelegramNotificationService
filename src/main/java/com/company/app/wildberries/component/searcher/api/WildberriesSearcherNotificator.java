package com.company.app.wildberries.component.searcher.api;

import com.company.app.wildberries.component.desire_lot.data.ResponseProducts;
import com.company.app.wildberries.component.searcher.data.WildberriesSearcherContainer;

public interface WildberriesSearcherNotificator {

	void notify(ResponseProducts responseProducts, WildberriesSearcherContainer wildberriesSearcherContainer);
}
