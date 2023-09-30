package com.company.app.wildberries_desire_lot.component;

import com.company.app.core.GetRequestHandler;
import com.company.app.core.temp.data.Response;
import com.company.app.core.temp.data.ResponseProducts;
import com.company.app.core.temp.tool.json.JsonTool;
import com.company.app.core.temp.tool.json.MapperSettings;
import com.company.app.core.util.Strings;
import com.company.app.wildberries_desire_lot.domain.entity.Desire;
import com.company.app.wildberries_desire_lot.domain.entity.DesireLot;
import com.company.app.wildberries_desire_lot.domain.repository.DesireLotRepository;
import com.company.app.wildberries_desire_lot.domain.repository.DesireRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static java.util.stream.Collectors.joining;

@Slf4j
@Service
@AllArgsConstructor
public class WildberriesDesireLotRefresher {

    private final DesireRepository desireRepository;
    private final DesireLotRepository desireLotRepository;
    private final GetRequestHandler getRequestHandler;
    private final JsonTool<Response> jsonTool;

    @Transactional
    public void refresh() {
        List<Desire> desireList = desireRepository.findAll();

        String collect = desireList.stream()
                .map(Desire::getArticle)
                .filter(StringUtils::isNotEmpty)
                .distinct()
                .collect(joining(";"));

        String urlForPriceSearch = WildberriesDesireLotUrlCreator.getUrlForPriceSearch(collect);
        String jsonResponse = getRequestHandler.loadHtmlPage(urlForPriceSearch);

        Response response = jsonTool.toJavaAsObject(jsonResponse, Response.class, MapperSettings.builder()
                .failOnUnknownProperties(false)
                .build());

        List<ResponseProducts> products = response.getData().getProducts();

        List<DesireLot> desireLotList = products.stream()
                .map(WildberriesDesireLotRefresher::toDesireLot)
                .toList();

        desireList.forEach(desire ->
            desireLotList.stream()
                    .filter(desireLot -> desire.getArticle().equals(desireLot.getArticle()))
                    .forEach(desire::setDesireLot)
        );

        desireLotRepository.saveAll(desireLotList);
        desireRepository.saveAll(desireList);
    }

    private static DesireLot toDesireLot(ResponseProducts responseProducts) {
        String price = Strings.cutEnd(String.valueOf(responseProducts.getSalePriceU()), 2);
        return new DesireLot()
                .setArticle(String.valueOf(responseProducts.getId()))
                .setDescription(responseProducts.getName())
                .setPrice(new BigDecimal(price));
    }

}
