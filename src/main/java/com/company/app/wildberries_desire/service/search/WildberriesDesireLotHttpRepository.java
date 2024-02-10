package com.company.app.wildberries_desire.service.search;

import java.math.BigDecimal;
import java.util.List;

import com.company.app.core.GetRequestHandler;
import com.company.app.core.temp.data.Response;
import com.company.app.core.temp.data.ResponseProducts;
import com.company.app.core.temp.tool.json.JsonTool;
import com.company.app.core.temp.tool.json.MapperSettings;
import com.company.app.core.util.Strings;
import com.company.app.core.wildberries_common.WildberriesUrlCreator;
import com.company.app.wildberries_desire.domain.entity.Desire;
import com.company.app.wildberries_desire.domain.entity.DesireLot;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import static java.util.stream.Collectors.joining;


@Service
@RequiredArgsConstructor
public class WildberriesDesireLotHttpRepository {

    private final GetRequestHandler getRequestHandler;
    private final JsonTool<Response> jsonTool;

    public List<DesireLot> findAllByHttp(List<Desire> desireList) {
        String articles = desireList.stream()
                .map(Desire::getArticle)
                .filter(StringUtils::isNotEmpty)
                .distinct()
                .collect(joining(";"));

        String urlForPriceSearch = WildberriesUrlCreator.getUrlForPriceSearch(articles);
        String jsonResponse = getRequestHandler.loadHtmlPage(urlForPriceSearch);

        Response response = jsonTool.toJavaAsObject(jsonResponse, Response.class, MapperSettings.builder()
                .failOnUnknownProperties(false)
                .build());

        List<ResponseProducts> products = response.getData().getProducts();

        return products.stream()
                .map(this::toDesireLot)
                .toList();
    }

    private DesireLot toDesireLot(ResponseProducts responseProducts) {
        String price = Strings.cutEnd(String.valueOf(responseProducts.getSalePriceU()), 2);
        return new DesireLot()
                .setArticle(String.valueOf(responseProducts.getId()))
                .setDescription(responseProducts.getName())
                .setPrice(new BigDecimal(price));
    }

}
