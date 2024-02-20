package com.company.app.wildberries.search;

import com.company.app.core.aop.logging.performance.PerformanceLogAnnotation;
import com.company.app.wildberries.search.service.WildberriesSearcherHandler;
import com.company.app.wildberries.search.service.data.WildberriesSearcherContext;
import com.company.app.wildberries.search.service.data.WildberriesSearcherResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class WildberriesSearcherFacade {

    private final WildberriesSearcherHandler wildberriesSearcherHandler;

    @PerformanceLogAnnotation
    public WildberriesSearcherResult search(WildberriesSearcherContext wildberriesSearcherContainer) {
        return wildberriesSearcherHandler.process(wildberriesSearcherContainer);
    }

}
