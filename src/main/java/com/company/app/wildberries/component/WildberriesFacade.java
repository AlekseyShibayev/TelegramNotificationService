package com.company.app.wildberries.component;

import com.company.app.core.aop.logging.performance.PerformanceLogAnnotation;
import com.company.app.wildberries.component.desire_lot.api.WildberriesService;
import com.company.app.wildberries.component.searcher.api.WildberriesSearcherHandler;
import com.company.app.wildberries.component.searcher.data.WildberriesSearcherContainer;
import com.company.app.wildberries.component.searcher.data.WildberriesSearcherResult;
import com.company.app.wildberries.domain.entity.FoundItem;
import com.company.app.wildberries.domain.service.api.FoundItemsService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Setter
@Component
public class WildberriesFacade {

	@Autowired
	private WildberriesService wildberriesService;
	@Autowired
	private FoundItemsService foundItemsService;
	@Autowired
	private WildberriesSearcherHandler wildberriesSearcherHandler;

	@PerformanceLogAnnotation
	public List<FoundItem> getDesiredLots() {
		return wildberriesService.getDesiredLots();
	}

	@PerformanceLogAnnotation
	public List<FoundItem> getAllFoundItems() {
		return foundItemsService.getAll();
	}

	@PerformanceLogAnnotation
	public WildberriesSearcherResult search(WildberriesSearcherContainer wildberriesSearcherContainer) {
		return wildberriesSearcherHandler.process(wildberriesSearcherContainer);
	}
}
