package com.company.app.wildberries_searcher.component.util;

import com.company.app.wildberries_searcher.component.data.WildberriesSearcherContext;
import com.google.common.collect.ImmutableMap;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Map;

@Slf4j
@UtilityClass
public class WildberriesSearcherProductsUrlCreator {

    private static final Map<String, String> WILDBERRIES_SIZE_MAPPING = ImmutableMap.<String, String>builder()
            .put("36", "31512")
            .put("36.5", "56141")
            .put("37", "32494")
            .put("37.5", "56142")
            .put("38", "33476")
            .put("38.5", "56144")
            .put("39", "34458")
            .put("39.5", "56139")
            .put("40", "35440")
            .put("40.5", "56151")
            .put("41", "36422")
            .put("41.5", "56157")
            .put("42", "37404")
            .put("42.5", "56158")
            .put("43", "38386")
            .put("43.5", "56167")
            .put("44", "39368")
            .put("44.5", "56168")
            .put("45", "40350")
            .put("45.5", "56177")
            .put("46", "41332")
            .put("48", "43296")
            .put("50", "45260")
            .put("51", "56206")
            .put("52", "56208")
            .put("54", "56218")
            .build();

    private static final String URL = "https://catalog.wb.ru/sellers/catalog?" +
            "appType=1" +
            "&curr=rub" +
            "&dest=-3827418" +
            "&regions=80,64,38,4,115,83,33,68,30,86,40,1,66,48,110,31,22,114" +
            "&sort=popular" +
            "&spp=22";

    public static String createUrl(WildberriesSearcherContext wildberriesSearcherContainer) {
        String withGender = withGender(wildberriesSearcherContainer.getGender());
        String withSize = withSize(wildberriesSearcherContainer.getDressSize());
        String supplier = withSupplier(wildberriesSearcherContainer.getSupplier());
        String withPage = withPage();
        String url = URL + withGender + withSize + supplier + withPage;
        log.debug("chatName [{}]: url:\n[{}].", wildberriesSearcherContainer.getChatName(), url);
        return url;
    }

    private static String withGender(String gender) {
        int i = gender.equals("male") ? 1 : 2;
        return "&fkind=" + i;
    }

    private static String withSupplier(String supplier) {
        return "&supplier=" + supplier;
    }

    private static String withSize(String sizes) {
        String preparedSizes = Arrays.stream(sizes.split(";"))
                .map(WILDBERRIES_SIZE_MAPPING::get)
                .reduce((s1, s2) -> s1 + ";" + s2)
                .orElseThrow();

        String urlSubstring = "&fsize=%s";
        return String.format(urlSubstring, preparedSizes);
    }

    private static String withPage() {
        return "&page=%s";
    }
}
