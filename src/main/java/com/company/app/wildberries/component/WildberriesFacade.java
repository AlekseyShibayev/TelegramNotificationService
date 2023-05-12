package com.company.app.wildberries.component;

import com.company.app.core.aop.logging.performance.PerformanceLogAnnotation;
import com.company.app.wildberries.component.api.WildberriesService;
import com.company.app.wildberries.component.searcher.api.WildberriesSearcher;
import com.company.app.wildberries.domain.dto.WildberriesLinkDto;
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
	private WildberriesSearcher wildberriesSearcher;

	@PerformanceLogAnnotation
	public List<FoundItem> getDesiredLots() {
		return wildberriesService.getDesiredLots();
	}

	@PerformanceLogAnnotation
	public List<FoundItem> getAllFoundItems() {
		return foundItemsService.getAll();
	}

	@PerformanceLogAnnotation
	public List<WildberriesLinkDto> search() {
		return wildberriesSearcher.search();
	}
}
