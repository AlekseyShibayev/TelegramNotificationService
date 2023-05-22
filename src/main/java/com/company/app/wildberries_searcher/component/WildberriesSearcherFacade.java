package com.company.app.wildberries_searcher.component;

import com.company.app.core.aop.logging.performance.PerformanceLogAnnotation;
import com.company.app.wildberries_searcher.component.api.WildberriesSearcherHandler;
import com.company.app.wildberries_searcher.component.data.WildberriesSearcherContainer;
import com.company.app.wildberries_searcher.component.data.WildberriesSearcherResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WildberriesSearcherFacade {

	@Autowired
	private WildberriesSearcherHandler wildberriesSearcherHandler;

	@PerformanceLogAnnotation
	public WildberriesSearcherResult search(WildberriesSearcherContainer wildberriesSearcherContainer) {
		return wildberriesSearcherHandler.process(wildberriesSearcherContainer);
	}
}
