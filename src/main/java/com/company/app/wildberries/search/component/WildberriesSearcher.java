package com.company.app.wildberries.search.component;

import java.util.List;

import com.company.app.wildberries.common.model.ResponseProducts;
import com.company.app.wildberries.search.component.data.WildberriesSearcherContext;
import com.company.app.wildberries.search.component.data.util.WildberriesSearcherProductsUrlCreator;
import com.company.app.wildberries.search.domain.dto.WildberriesLinkDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


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
