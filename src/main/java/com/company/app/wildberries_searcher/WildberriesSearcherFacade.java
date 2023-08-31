package com.company.app.wildberries_searcher;

import com.company.app.core.aop.logging.performance.PerformanceLogAnnotation;
import com.company.app.wildberries_searcher.component.WildberriesSearcherHandler;
import com.company.app.wildberries_searcher.component.data.WildberriesSearcherContext;
import com.company.app.wildberries_searcher.component.data.WildberriesSearcherResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WildberriesSearcherFacade {

    @Autowired
    private WildberriesSearcherHandler wildberriesSearcherHandler;

    @PerformanceLogAnnotation
    public WildberriesSearcherResult search(WildberriesSearcherContext wildberriesSearcherContainer) {
        return wildberriesSearcherHandler.process(wildberriesSearcherContainer);
    }
}
