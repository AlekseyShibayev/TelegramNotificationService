package com.company.app.wildberries.desire.service.search;

import java.math.BigDecimal;
import java.util.List;

import com.company.app.common.tool.HttpService;
import com.company.app.common.tool.json.JsonMapper;
import com.company.app.common.tool.json.MapperSettings;
import com.company.app.core.util.Strings;
import com.company.app.wildberries.common.util.WildberriesUrlCreator;
import com.company.app.wildberries.common.model.VmResponse;
import com.company.app.wildberries.common.model.VmProduct;
import com.company.app.wildberries.desire.domain.entity.Desire;
import com.company.app.wildberries.desire.domain.entity.DesireLot;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import static java.util.stream.Collectors.joining;


@Service
@RequiredArgsConstructor
public class WildberriesDesireLotHttpRepository {

    private final HttpService getRequestHandler;
    private final JsonMapper<VmResponse> jsonTool;

    public List<DesireLot> findAllByHttp(List<Desire> desireList) {
        String articles = desireList.stream()
            .map(Desire::getArticle)
            .filter(StringUtils::isNotEmpty)
            .distinct()
            .collect(joining(";"));

        String urlForPriceSearch = WildberriesUrlCreator.getUrlForPriceSearch(articles);
        String jsonResponse = getRequestHandler.get(urlForPriceSearch).get();

        VmResponse response = jsonTool.toJavaAsObject(jsonResponse, VmResponse.class, new MapperSettings().setFailOnUnknownProperties(false));

        List<VmProduct> products = response.getData().getProducts();

        return products.stream()
            .map(this::toDesireLot)
            .toList();
    }

    private DesireLot toDesireLot(VmProduct responseProducts) {
        String price = Strings.cutEnd(String.valueOf(responseProducts.getSalePriceU()), 2);
        return new DesireLot()
            .setArticle(String.valueOf(responseProducts.getId()))
            .setDescription(responseProducts.getName())
            .setPrice(new BigDecimal(price));
    }

}