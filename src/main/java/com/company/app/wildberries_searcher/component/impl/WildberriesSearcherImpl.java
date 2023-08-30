package com.company.app.wildberries_searcher.component.impl;

import com.company.app.infrastructure.data.ResponseProducts;
import com.company.app.wildberries_searcher.component.api.WildberriesSearcher;
import com.company.app.wildberries_searcher.component.api.WildberriesSearcherExtractor;
import com.company.app.wildberries_searcher.component.api.WildberriesSearcherFilterer;
import com.company.app.wildberries_searcher.component.data.WildberriesSearcherContext;
import com.company.app.wildberries_searcher.component.util.WildberriesSearcherProductsUrlCreator;
import com.company.app.wildberries_searcher.domain.dto.WildberriesLinkDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class WildberriesSearcherImpl implements WildberriesSearcher {

    @Autowired
    private WildberriesSearcherExtractor wildberriesSearcherExtractor;
    @Autowired
    private WildberriesSearcherFilterer wildberriesSearcherFilterer;

    @Override
    public List<WildberriesLinkDto> search(WildberriesSearcherContext context) {
        String url = WildberriesSearcherProductsUrlCreator.createUrl(context);
        List<ResponseProducts> products = wildberriesSearcherExtractor.extractResponseProducts(url);
        List<ResponseProducts> filteredProducts = wildberriesSearcherFilterer.filter(products, context);
        return filteredProducts.stream()
                .map(ResponseProducts::toLinkDto)
                .distinct()
                .toList();
    }
}
