package com.company.app.wildberries.search.service.average_price;

import com.company.app.common.tool.CaptchaFighter;
import com.company.app.wildberries.common.model.VmProduct;
import com.company.app.wildberries.common.price_history.WbHistoryFinder;
import com.company.app.wildberries.common.price_history.domain.entity.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.OptionalDouble;


@Slf4j
@Service
@RequiredArgsConstructor
public class WbAveragePriceCalculator {

    private final WbHistoryFinder wbHistoryFinder;
    private final CaptchaFighter captchaFighter;

    public AveragePriceRegistry createAveragePriceRegistry(List<VmProduct> products) {
        AveragePriceRegistry registry = AveragePriceRegistry.of(products);
        for (VmProduct product : products) {
            BigDecimal averagePrice = getAveragePriceForOne(product);
            registry.put(product, averagePrice);
            captchaFighter.fight(1000, 5000);
        }
        return registry;
    }

    private BigDecimal getAveragePriceForOne(VmProduct product) {
        try {
            Integer id = product.getId();
            Product entity = wbHistoryFinder.findHistoryBy(String.valueOf(id));

            OptionalDouble average = entity.getPrice().stream()
                    .mapToInt(price -> Integer.parseInt(price.getCost()))
                    .average();

            BigDecimal bigDecimal = BigDecimal.valueOf(average.orElse(0.00));
            return bigDecimal.setScale(2, RoundingMode.HALF_UP);
        } catch (Exception e) {
            log.error("for product with id [{}] can not find average price, because: [{}]", product.getId(), e.getMessage(), e);
            return BigDecimal.ZERO;
        }
    }

}