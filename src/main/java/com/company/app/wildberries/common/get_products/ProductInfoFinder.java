package com.company.app.wildberries.common.get_products;

import com.company.app.common.tool.CaptchaFighter;
import com.company.app.common.tool.HttpService;
import com.company.app.common.tool.json.JsonMapper;
import com.company.app.common.tool.json.MapperSettings;
import com.company.app.wildberries.common.model.VmResponse;
import com.google.common.collect.Iterables;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class ProductInfoFinder {

    private static final String LEFT_PART_OF_URL = "https://card.wb.ru/cards/v2/detail?appType=1&curr=rub&dest=12354108&spp=30&ab_testing=false&nm=";
    private static final Integer BATCH_SIZE = 6;

    private final HttpService httpService;
    private final JsonMapper<VmResponse> jsonMapper;
    private final ProductInfoMapper productInfoMapper;
    private final CaptchaFighter captchaFighter;

    public List<ProductInfo> find(Collection<String> articles) {
        List<ProductInfo> result = new ArrayList<>();

        Set<String> validatedArticles = articles.stream()
                .filter(StringUtils::isNotBlank)
                .collect(Collectors.toSet());

        Iterator<List<String>> iterator = Iterables.partition(validatedArticles, BATCH_SIZE).iterator();
        iterator.forEachRemaining(strings -> {
            String url = getUrl(strings);
            List<ProductInfo> chunk = findByHttp(url);
            result.addAll(chunk);

            if (iterator.hasNext()) {
                captchaFighter.fight(1500, 5000);
            }
        });

        return result;
    }

    private String getUrl(List<String> strings) {
        String rightPartOfUrl = String.join(";", strings);
        return LEFT_PART_OF_URL + rightPartOfUrl;
    }

    private List<ProductInfo> findByHttp(String url) {
        return httpService.get(url)
                .map(this::toProductInfoDtoList)
                .orElseGet(ArrayList::new);
    }

    private List<ProductInfo> toProductInfoDtoList(String jsonResponse) {
        var response = jsonMapper.toJavaAsObject(jsonResponse, VmResponse.class, new MapperSettings().setFailOnUnknownProperties(false));
        return response.getData().getProducts()
                .stream()
                .map(productInfoMapper::map)
                .toList();
    }

}
