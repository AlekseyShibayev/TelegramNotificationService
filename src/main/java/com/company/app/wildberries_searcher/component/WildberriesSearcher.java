package com.company.app.wildberries_searcher.component;

import com.company.app.core.temp.data.ResponseProducts;
import com.company.app.wildberries_searcher.component.data.WildberriesSearcherContext;
import com.company.app.wildberries_searcher.component.data.util.WildberriesSearcherProductsUrlCreator;
import com.company.app.wildberries_searcher.domain.dto.WildberriesLinkDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class WildberriesSearcher {

    private final WildberriesSearcherExtractor wildberriesSearcherExtractor;
    private final WildberriesSearcherFilterer wildberriesSearcherFilterer;

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
