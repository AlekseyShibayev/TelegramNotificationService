package com.company.app.wildberries.common.get_products;

import com.company.app.common.tool.HttpService;
import com.company.app.common.tool.json.JsonMapper;
import com.company.app.common.tool.json.MapperSettings;
import com.company.app.wildberries.common.model.VmResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.joining;


@Slf4j
@Service
@RequiredArgsConstructor
public class ProductInfoDtoFinder {

    private static final String LEFT_PART_OF_URL = "https://card.wb.ru/cards/v2/detail?appType=1&curr=rub&dest=12354108&spp=30&ab_testing=false&nm=";

    private final HttpService httpService;
    private final JsonMapper<VmResponse> jsonMapper;
    private final ProductInfoDtoMapper productInfoDtoMapper;

    public List<ProductInfoDto> find(Collection<String> articles) {
        String rightPartOfUrl = articles.stream()
                .filter(StringUtils::isNotBlank)
                .distinct()
                .collect(joining(";"));

        String url = LEFT_PART_OF_URL + rightPartOfUrl;

        return httpService.get(url)
                .map(this::getDesireLots)
                .orElseGet(ArrayList::new);
    }

    private List<ProductInfoDto> getDesireLots(String jsonResponse) {
        var response = jsonMapper.toJavaAsObject(jsonResponse, VmResponse.class, new MapperSettings().setFailOnUnknownProperties(false));
        return response.getData().getProducts()
                .stream()
                .map(productInfoDtoMapper::map)
                .toList();
    }

}
