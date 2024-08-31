package com.company.app.wildberries.desire;

import com.company.app.core.aop.logging.performance.PerformanceLogAnnotation;
import com.company.app.telegram.TelegramFacade;
import com.company.app.wildberries.common.get_products.ProductInfoDto;
import com.company.app.wildberries.common.get_products.ProductInfoDtoFinder;
import com.company.app.wildberries.common.util.WildberriesUrlCreator;
import com.company.app.wildberries.desire.domain.entity.Desire;
import com.company.app.wildberries.desire.domain.repository.DesireRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class WildberriesDesireSearcher {

    private final DesireRepository desireRepository;
    private final ProductInfoDtoFinder productInfoDtoFinder;
    private final TelegramFacade telegramFacade;

    @PerformanceLogAnnotation
    @Transactional
    public void search() {
        List<Desire> desireList = desireRepository.findAll();
        Map<String, ProductInfoDto> articleVsProductInfoDto = findByHttpAndGetAsMap(desireList);

        desireList.forEach(desire -> {
            ProductInfoDto productInfoDto = articleVsProductInfoDto.get(desire.getArticle());
            if (productInfoDto != null && isPriceMatches(desire, productInfoDto)) {
                String urlForResponse = WildberriesUrlCreator.getUrlForResponse(desire.getArticle());
                telegramFacade.writeToTargetChat(desire.getChatName(), urlForResponse);
            }
        });
    }

    private boolean isPriceMatches(Desire desire, ProductInfoDto productInfoDto) {
        BigDecimal desirePrice = desire.getPrice();
        BigDecimal realPrice = productInfoDto.getPrice();
        if (BigDecimal.ZERO.equals(realPrice)) {
            return false;
        }
        return PriceMatcher.le(desirePrice, realPrice);
    }

    private  Map<String, ProductInfoDto> findByHttpAndGetAsMap(List<Desire> desireList) {
        List<String> articles = desireList.stream().map(Desire::getArticle).toList();
        List<ProductInfoDto> productInfoDtoList = productInfoDtoFinder.find(articles);
        return productInfoDtoList.stream().collect(Collectors.toMap(ProductInfoDto::getArticle, Function.identity()));
    }

}
