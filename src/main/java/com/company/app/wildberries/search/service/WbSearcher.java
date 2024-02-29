package com.company.app.wildberries.search.service;

import com.company.app.telegram.TelegramFacade;
import com.company.app.wildberries.common.model.VmProduct;
import com.company.app.wildberries.common.price_history.WbHistoryFinder;
import com.company.app.wildberries.common.price_history.domain.entity.Product;
import com.company.app.wildberries.search.domain.dto.LinkDto;
import com.company.app.wildberries.search.model.WbSearchContext;
import com.company.app.wildberries.search.service.average_price.AveragePriceRegistry;
import com.company.app.wildberries.search.service.filter.WbFilter;
import com.company.app.wildberries.search.util.WbBrandUrlCreator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class WbSearcher {

    private final WbSearcherApi wbSearcherApi;
    private final WbHistoryFinder wbHistoryFinder;
    private final List<WbFilter> wbFilterList;
    private final TelegramFacade telegramFacade;

    public List<LinkDto> search(WbSearchContext context) {
        String urlWithEmptyPage = WbBrandUrlCreator.createUrlWithEmptyPage(context);

        List<VmProduct> products = wbSearcherApi.findProductsByWeb(urlWithEmptyPage);

        List<VmProduct> filteredProducts = filter(products, context);

        List<LinkDto> linkDtoList = filteredProducts.stream().map(VmProduct::toLinkDto).distinct().toList();

        linkDtoList.forEach(linkDto -> telegramFacade.writeToTargetChat(context.getChatName(), linkDto.toMessage()));
        return linkDtoList;
    }

    private List<VmProduct> filter(List<VmProduct> products, WbSearchContext context) {
        List<VmProduct> preFilteredProducts = products.stream()
                .filter(responseProducts -> filterAll(responseProducts, context))
                .toList();
        log.debug("[{}]: [{}] after pre filtering", context.getChatName(), preFilteredProducts.size());

        AveragePriceRegistry averagePriceRegistry = createAveragePriceRegistry(preFilteredProducts);
        List<VmProduct> postFilteredProducts = preFilteredProducts.stream()
                .filter(product -> averagePriceRegistry.isCurrentPriceLesserThanAveragePrice(product, context))
                .toList();
        log.debug("[{}]: [{}] after post filtering", context.getChatName(), postFilteredProducts.size());
        return postFilteredProducts;
    }

    private boolean filterAll(VmProduct product, WbSearchContext context) {
        return wbFilterList.stream().allMatch(wbFilter -> wbFilter.test(product, context));
    }

    public AveragePriceRegistry createAveragePriceRegistry(List<VmProduct> products) {
        List<String> articleList = products.stream().map(product -> String.valueOf(product.getId())).collect(Collectors.toList());
        List<Product> persistProductList = wbHistoryFinder.findHistoryBy(articleList);
        return AveragePriceRegistry.of(persistProductList);
    }

}