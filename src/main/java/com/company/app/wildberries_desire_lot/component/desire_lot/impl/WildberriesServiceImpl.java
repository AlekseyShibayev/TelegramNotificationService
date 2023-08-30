package com.company.app.wildberries_desire_lot.component.desire_lot.impl;

import com.company.app.core.util.Logs;
import com.company.app.common.GetRequestHandler;
import com.company.app.wildberries_desire_lot.component.desire_lot.api.WildberriesPriceExtractor;
import com.company.app.wildberries_desire_lot.component.desire_lot.api.WildberriesService;
import com.company.app.wildberries_desire_lot.component.desire_lot.util.WBUtils;
import com.company.app.wildberries_desire_lot.domain.entity.FoundItem;
import com.company.app.wildberries_desire_lot.domain.entity.Lot;
import com.company.app.wildberries_desire_lot.domain.repository.LotRepository;
import com.company.app.wildberries_desire_lot.domain.service.api.FoundItemsService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Setter
@Component
public class WildberriesServiceImpl implements WildberriesService {

    @Autowired
    private WildberriesPriceExtractor wildberriesPriceExtractor;
    @Autowired
    private GetRequestHandler getRequestHandler;
    @Autowired
    private LotRepository lotRepository;
    @Autowired
    private FoundItemsService foundItemsService;

    public List<FoundItem> getDesiredLots() {
        List<Lot> lots = lotRepository.findAll();
        String url = WBUtils.getUrlForPriceSearch(lots);
        String htmlResponse = getRequestHandler.getResponseBodyAsString(url);
        List<FoundItem> items = lots.stream()
                .filter(lot -> isDesireLot(htmlResponse, lot))
                .map(lot -> FoundItem.builder().article(lot.getArticle()).creationDate(new Date()).build())
                .collect(Collectors.toList());
        foundItemsService.saveAll(items);
        return items;
    }

    private boolean isDesireLot(String htmlResponse, Lot lot) {
        try {
            String currentPriceString = wildberriesPriceExtractor.extract(htmlResponse, lot.getArticle());
            BigDecimal currentPrice = getCurrentPrice(lot, currentPriceString);
            BigDecimal desiredPrice = new BigDecimal(lot.getDesiredPrice() + "00.00");
            int i = desiredPrice.compareTo(currentPrice);
            return i > 0;
        } catch (Exception e) {
            Logs.doExceptionLog(log, e, "проблема с [%s]".formatted(lot.toString()));
            return false;
        }
    }

    private BigDecimal getCurrentPrice(Lot lot, String currentPriceString) {
        String discount = lot.getDiscount();
        BigDecimal currentPrice = new BigDecimal(currentPriceString);
        BigDecimal multiply = currentPrice.multiply(new BigDecimal(discount));
        currentPrice = currentPrice.subtract(multiply);
        return currentPrice;
    }
}
