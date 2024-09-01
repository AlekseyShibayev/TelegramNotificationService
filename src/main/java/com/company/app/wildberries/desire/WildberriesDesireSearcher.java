package com.company.app.wildberries.desire;

import com.company.app.core.aop.logging.performance.PerformanceLogAnnotation;
import com.company.app.telegram.TelegramFacade;
import com.company.app.wildberries.common.get_products.ProductInfo;
import com.company.app.wildberries.common.get_products.ProductInfoFinder;
import com.company.app.wildberries.common.util.PriceMatcher;
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
    private final ProductInfoFinder productInfoFinder;
    private final TelegramFacade telegramFacade;

    @PerformanceLogAnnotation
    @Transactional
    public void search() {
        List<Desire> desireList = desireRepository.findAll();
        Map<String, ProductInfo> articleVsProductInfoDto = findByHttpAndGetAsMap(desireList);

        List<DesireContext> context = desireList.stream()
                .filter(desire -> articleVsProductInfoDto.get(desire.getArticle()) != null)
                .map(desire -> createContext(articleVsProductInfoDto, desire))
                .toList();

        fillDescription(context);
        notify(context);
    }

    private Map<String, ProductInfo> findByHttpAndGetAsMap(List<Desire> desireList) {
        List<String> articles = desireList.stream().map(Desire::getArticle).toList();
        List<ProductInfo> productInfoDtoList = productInfoFinder.find(articles);
        return productInfoDtoList.stream().collect(Collectors.toMap(ProductInfo::getArticle, Function.identity()));
    }

    private DesireContext createContext(Map<String, ProductInfo> articleVsProductInfoDto, Desire desire) {
        return new DesireContext()
                .setDesire(desire)
                .setProductInfoDto(articleVsProductInfoDto.get(desire.getArticle()));
    }

    private void fillDescription(List<DesireContext> list) {
        list.stream()
                .filter(desireContext -> !desireContext.getProductInfoDto().getDescription().equals(desireContext.getDesire().getDescription()))
                .forEach(desireContext -> desireContext.getDesire().setDescription(desireContext.getProductInfoDto().getDescription()));
    }

    private void notify(List<DesireContext> list) {
        list.stream()
                .filter(this::realPriceLesserThenDesirePrice)
                .forEach(this::notify);
    }

    private void notify(DesireContext desireContext) {
        String urlForResponse = WildberriesUrlCreator.getProductUrl(desireContext.getDesire().getArticle());
        telegramFacade.writeToTargetChat(desireContext.getDesire().getChatName(), urlForResponse);
    }

    private boolean realPriceLesserThenDesirePrice(DesireContext desireContext) {
        BigDecimal desirePrice = desireContext.getDesire().getPrice();
        BigDecimal realPrice = desireContext.getProductInfoDto().getPrice();
        if (BigDecimal.ZERO.equals(realPrice)) {
            return false;
        }
        return PriceMatcher.le(realPrice, desirePrice);
    }

}
