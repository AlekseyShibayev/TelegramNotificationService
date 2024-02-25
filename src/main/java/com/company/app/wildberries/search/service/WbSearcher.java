package com.company.app.wildberries.search.service;

import com.company.app.telegram.TelegramFacade;
import com.company.app.wildberries.common.model.VmProduct;
import com.company.app.wildberries.search.domain.dto.LinkDto;
import com.company.app.wildberries.search.model.WbSearchContext;
import com.company.app.wildberries.search.service.average_price.AveragePriceRegistry;
import com.company.app.wildberries.search.service.average_price.WbAveragePriceCalculator;
import com.company.app.wildberries.search.service.filter.WbFilter;
import com.company.app.wildberries.search.util.WbBrandUrlCreator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class WbSearcher {

    private final WbSearcherApi wbSearcherApi;
    private final WbAveragePriceCalculator wbAveragePriceCalculator;
    private final List<WbFilter> wbFilterList;
    private final TelegramFacade telegramFacade;

    public List<LinkDto> search(WbSearchContext context) {
        String urlWithEmptyPage = WbBrandUrlCreator.createUrlWithEmptyPage(context);

        List<VmProduct> products = wbSearcherApi.findProductsByWeb(urlWithEmptyPage);

        List<VmProduct> filteredProducts = filter(products, context);

        return filteredProducts.stream()
                .map(VmProduct::toLinkDto)
                .distinct()
                .peek(message -> telegramFacade.writeToTargetChat(context.getChatName(), message))
                .toList();
    }

    private List<VmProduct> filter(List<VmProduct> products, WbSearchContext context) {
        List<VmProduct> preFilteredProducts = products.stream()
                .filter(responseProducts -> filterAll(responseProducts, context))
                .toList();
        log.debug("[{}]: После предварительной фильтрации осталось [{}] шт.", context.getChatName(), preFilteredProducts.size());

        AveragePriceRegistry averagePriceRegistry = wbAveragePriceCalculator.createAveragePriceRegistry(preFilteredProducts);
        return preFilteredProducts.stream()
                .filter(product -> averagePriceRegistry.isCurrentPriceLesserThanAveragePrice(product, context))
                .toList();
    }

    private boolean filterAll(VmProduct product, WbSearchContext context) {
        for (WbFilter filter : wbFilterList) {
            boolean result = filter.test(product, context);
            if (!result) {
                return false;
            }

        }
        return true;
    }

}