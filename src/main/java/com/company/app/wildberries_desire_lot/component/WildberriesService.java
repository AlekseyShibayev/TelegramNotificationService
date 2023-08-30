package com.company.app.wildberries_desire_lot.component;

import com.company.app.common.GetRequestHandler;
import com.company.app.core.util.Logs;
import com.company.app.wildberries_desire_lot.component.util.WBUtils;
import com.company.app.wildberries_desire_lot.domain.entity.FoundItem;
import com.company.app.wildberries_desire_lot.domain.entity.DesireLot;
import com.company.app.wildberries_desire_lot.domain.repository.DesireLotRepository;
import com.company.app.wildberries_desire_lot.domain.service.FoundItemsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class WildberriesService {

    @Autowired
    private WildberriesPriceExtractor wildberriesPriceExtractor;
    @Autowired
    private GetRequestHandler getRequestHandler;
    @Autowired
    private DesireLotRepository lotRepository;
    @Autowired
    private FoundItemsService foundItemsService;

    public List<FoundItem> getDesiredLots() {
        List<DesireLot> lots = lotRepository.findAll();
        String url = WBUtils.getUrlForPriceSearch(lots);
        String htmlResponse = getRequestHandler.getResponseBodyAsString(url);
        List<FoundItem> items = lots.stream()
                .filter(lot -> isDesireLot(htmlResponse, lot))
                .map(lot -> new FoundItem().setArticle(lot.getArticle()).setCreationDate(new Date()))
                .collect(Collectors.toList());
        foundItemsService.saveAll(items);
        return items;
    }

    private boolean isDesireLot(String htmlResponse, DesireLot lot) {
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

    private BigDecimal getCurrentPrice(DesireLot lot, String currentPriceString) {
        String discount = lot.getDiscount();
        BigDecimal currentPrice = new BigDecimal(currentPriceString);
        BigDecimal multiply = currentPrice.multiply(new BigDecimal(discount));
        currentPrice = currentPrice.subtract(multiply);
        return currentPrice;
    }

}
