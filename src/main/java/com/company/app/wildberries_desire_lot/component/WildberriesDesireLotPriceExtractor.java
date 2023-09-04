package com.company.app.wildberries_desire_lot.component;

import com.company.app.core.temp.data.Response;
import com.company.app.core.temp.data.ResponseProducts;
import com.company.app.core.temp.tool.json.JsonTool;
import com.company.app.core.temp.tool.json.MapperSettings;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class WildberriesDesireLotPriceExtractor {

    private final JsonTool<Response> jsonTool;

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
