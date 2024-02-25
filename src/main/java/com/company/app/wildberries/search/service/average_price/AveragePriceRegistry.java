package com.company.app.wildberries.search.service.average_price;

import com.company.app.core.util.Strings;
import com.company.app.wildberries.common.model.VmProduct;
import com.company.app.wildberries.search.model.WbSearchContext;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Accessors(chain = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AveragePriceRegistry {

    private int position;
    private int productsSize;
    private Map<Integer, BigDecimal> map;

    public static AveragePriceRegistry of(List<VmProduct> products) {
        AveragePriceRegistry averagePriceRegistry = new AveragePriceRegistry();
        averagePriceRegistry.position = 1;
        averagePriceRegistry.productsSize = products.size();
        averagePriceRegistry.map = new HashMap<>();
        return averagePriceRegistry;
    }

    public void put(VmProduct product, BigDecimal averagePrice) {
        if (log.isDebugEnabled()) {
            BigDecimal currentPrice = mapToCurrentPrice(product);
            log.debug("[{}]: [{}] < средняя: [{}] ? [{}/{}]", product.getId(), currentPrice, averagePrice, position, productsSize);
            position++;
        }
        map.put(product.getId(), averagePrice);
    }

    public boolean isCurrentPriceLesserThanAveragePrice(VmProduct product, WbSearchContext context) {
        try {
            BigDecimal averagePrice = map.get(product.getId());
            BigDecimal currentPrice = mapToCurrentPrice(product);
            currentPrice = currentPrice.multiply(new BigDecimal(context.getGreedIndex()));
            int i = currentPrice.compareTo(averagePrice);
            return i < 0;
        } catch (Exception e) {
            log.error("[%s] Проблема с [%s], причина: %s".formatted(context.getChatName(), product.getId(), e.getMessage()), e);
            return false;
        }
    }

    private BigDecimal mapToCurrentPrice(VmProduct product) {
        Integer salePriceU = product.getSalePriceU();
        String currentPrice = Strings.cutEnd(String.valueOf(salePriceU), 2);
        return new BigDecimal(currentPrice);
    }

}