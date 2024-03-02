package com.company.app.wildberries.common.price_history.domain.service;

import java.time.ZonedDateTime;
import java.util.List;

import com.company.app.core.util.Collections;
import com.company.app.wildberries.common.price_history.domain.entity.Price;
import com.company.app.wildberries.common.price_history.domain.entity.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class WbHistoryChecker {

    public boolean isNeedLoadPriceHistory(Product product) {
        List<Price> priceList = product.getPrice();
        if (Collections.isEmpty(priceList)) {
            return true;
        } else {
            Price price = priceList.get(0);
            ZonedDateTime createDate = price.getCreateDate();
            return createDate.plusDays(3).isBefore(ZonedDateTime.now());
        }
    }

}