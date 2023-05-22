package com.company.app.wildberries_desire_lot.component;

import com.company.app.core.aop.logging.performance.PerformanceLogAnnotation;
import com.company.app.wildberries_desire_lot.component.desire_lot.api.WildberriesService;
import com.company.app.wildberries_desire_lot.domain.entity.FoundItem;
import com.company.app.wildberries_desire_lot.domain.service.api.FoundItemsService;
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

	@PerformanceLogAnnotation
	public List<FoundItem> getDesiredLots() {
		return wildberriesService.getDesiredLots();
	}

	@PerformanceLogAnnotation
	public List<FoundItem> getAllFoundItems() {
		return foundItemsService.getAll();
	}
}
