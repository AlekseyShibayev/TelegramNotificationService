package com.company.app.wildberries.search.service.average_price;

import com.company.app.common.tool.CaptchaFighter;
import com.company.app.wildberries.common.model.VmProduct;
import com.company.app.wildberries.common.price_history.WbHistoryFinder;
import com.company.app.wildberries.common.price_history.domain.entity.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
            registry.put(product, getAveragePriceForOne(product));
            captchaFighter.fight(1000, 5000);
        }
        return registry;
    }

    private BigDecimal getAveragePriceForOne(VmProduct product) {
        Integer id = product.getId();
        Product entity = wbHistoryFinder.findHistoryBy(String.valueOf(id));

        OptionalDouble average = entity.getPrice().stream()
                .mapToInt(price -> Integer.parseInt(price.getCost()))
                .average();
        return BigDecimal.valueOf(average.orElse(0.00));
    }

}