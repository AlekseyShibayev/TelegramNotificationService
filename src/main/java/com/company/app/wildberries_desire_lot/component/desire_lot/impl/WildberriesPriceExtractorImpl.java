package com.company.app.wildberries_desire_lot.component.desire_lot.impl;

import com.company.app.core.tool.json.JsonTool;
import com.company.app.core.tool.json.MapperSettings;
import com.company.app.common.data.Response;
import com.company.app.common.data.ResponseProducts;
import com.company.app.wildberries_desire_lot.component.desire_lot.api.WildberriesPriceExtractor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Setter
@Component
public class WildberriesPriceExtractorImpl implements WildberriesPriceExtractor {

    @Autowired
    private JsonTool<Response> jsonTool;

    public String extract(String jsonResponse, String id) {
        Response response = jsonTool.toJavaAsObject(jsonResponse, Response.class, MapperSettings.builder()
                .failOnUnknownProperties(false)
                .build());

        List<ResponseProducts> products = response.getData().getProducts();
        return products.stream()
                .filter(responseProducts -> responseProducts.getId().equals(Integer.valueOf(id)))
                .map(responseProducts -> responseProducts.getSalePriceU().toString())
                .findFirst()
                .orElseThrow();
    }

}
