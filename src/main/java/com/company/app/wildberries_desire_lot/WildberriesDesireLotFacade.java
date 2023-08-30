package com.company.app.wildberries_desire_lot;

import com.company.app.core.aop.logging.performance.PerformanceLogAnnotation;
import com.company.app.wildberries_desire_lot.component.WildberriesService;
import com.company.app.wildberries_desire_lot.domain.entity.FoundItem;
import com.company.app.wildberries_desire_lot.domain.service.FoundItemsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class WildberriesDesireLotFacade {

    private final WildberriesService wildberriesService;
    private final FoundItemsService foundItemsService;

    @PerformanceLogAnnotation
    public List<FoundItem> getDesiredLots() {
        return wildberriesService.getDesiredLots();
    }

    @PerformanceLogAnnotation
    public List<FoundItem> getAllFoundItems() {
        return foundItemsService.getAll();
    }

}
