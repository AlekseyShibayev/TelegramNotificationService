package com.company.app.wildberries.search;

import com.company.app.core.aop.logging.performance.PerformanceLogAnnotation;
import com.company.app.wildberries.search.model.WbSearchContext;
import com.company.app.wildberries.search.model.WbSearchResult;
import com.company.app.wildberries.search.service.WbSearcherSemaphoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class WbSearcherFacade {

    private final WbSearcherSemaphoreService wbSearcherSemaphoreService;

    @PerformanceLogAnnotation
    public WbSearchResult search(WbSearchContext context) {
        return wbSearcherSemaphoreService.tryStart(context);
    }

}
