package com.company.app.wildberries.search.service.average_price;

import com.company.app.core.util.Strings;
import com.company.app.wildberries.common.model.VmProduct;
import com.company.app.wildberries.common.price_history.domain.entity.Product;
import com.company.app.wildberries.search.model.WbSearchContext;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;

@Slf4j
@Accessors(chain = true)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class AveragePriceRegistry {

    private final Map<Integer, BigDecimal> articleVsAveragePrice;

    public static AveragePriceRegistry of(List<Product> persistProductList) {
        Map<Integer, BigDecimal> map = new HashMap<>();

        for (Product product : persistProductList) {
            String article = product.getArticle();
            BigDecimal averagePrice = getAveragePriceForOne(product);
            map.put(Integer.valueOf(article), averagePrice);
        }

        return new AveragePriceRegistry(map);
    }

    private static BigDecimal getAveragePriceForOne(Product product) {
        try {
            OptionalDouble average = product.getPrice().stream()
                    .mapToInt(price -> Integer.parseInt(price.getCost()))
                    .average();

            BigDecimal bigDecimal = BigDecimal.valueOf(average.orElse(0.00));
            return bigDecimal.setScale(2, RoundingMode.HALF_UP);
        } catch (Exception e) {
            log.error("for product with article [{}] can not find average price, because: [{}]", product.getArticle(), e.getMessage(), e);
            return BigDecimal.ZERO;
        }
    }

    public boolean isCurrentPriceLesserThanAveragePrice(VmProduct product, WbSearchContext context) {
        try {
            BigDecimal averagePrice = articleVsAveragePrice.get(product.getId());
            BigDecimal currentPrice = mapToCurrentPrice(product);
            log.debug("article [{}]: current price [{}], average price [{}], greed index [{}]", product.getId(), currentPrice, averagePrice, context.getGreedIndex());
            currentPrice = currentPrice.multiply(new BigDecimal(context.getGreedIndex()));
            int i = currentPrice.compareTo(averagePrice);
            log.debug("article [{}]:  [{}] < [{}]", product.getId(), currentPrice, averagePrice);
            return i < 0;
        } catch (Exception e) {
            log.error("[%s] Проблема с [%s], причина: %s".formatted(context.getChatName(), product.getId(), e.getMessage()), e);
            return false;
        }
    }

    private BigDecimal mapToCurrentPrice(VmProduct product) {
        Integer salePriceU = product.getSalePriceU();
        String currentPrice = Strings.cutEnd(String.valueOf(salePriceU), 2);
        return new BigDecimal(currentPrice).setScale(2, RoundingMode.HALF_UP);
    }

}