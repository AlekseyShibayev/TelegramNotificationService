package com.company.app.wildberries_desire_lot;

import com.company.app.core.aop.logging.performance.PerformanceLogAnnotation;
import com.company.app.wildberries_desire_lot.component.WildberriesDesireLotSearchHandler;
import com.company.app.wildberries_desire_lot.domain.entity.FoundItem;
import com.company.app.wildberries_desire_lot.domain.service.FoundItemsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class WildberriesDesireLotFacade {

    private final WildberriesDesireLotSearchHandler desireLotSearchHandler;
    private final FoundItemsService foundItemsService;

    @PerformanceLogAnnotation
    public List<FoundItem> doDesireLotSearch() {
        return desireLotSearchHandler.getDesiredLots();
    }

    @PerformanceLogAnnotation
    public List<FoundItem> getAllFoundItems() {
        return foundItemsService.findAll();
    }

}
