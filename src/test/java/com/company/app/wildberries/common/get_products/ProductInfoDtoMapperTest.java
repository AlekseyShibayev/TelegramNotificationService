package com.company.app.wildberries.common.get_products;

import com.company.app.common.tool.json.JsonMapper;
import com.company.app.common.tool.json.MapperSettings;
import com.company.app.configuration.SpringBootTestApplication;
import com.company.app.wildberries.common.model.VmResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

class ProductInfoDtoMapperTest extends SpringBootTestApplication {

    @Autowired
    private JsonMapper<VmResponse> jsonMapper;
    @Autowired
    private ProductInfoMapper productInfoDtoMapper;

    @Test
    void success_mapping_test() {
        var response = jsonMapper.toJavaAsObject(JSON_RESPONSE_EXAMPLE, VmResponse.class, new MapperSettings().setFailOnUnknownProperties(false));

        List<ProductInfo> list = response.getData().getProducts()
                .stream()
                .map(product -> productInfoDtoMapper.map(product))
                .toList();

        Map<String, ProductInfo> map = list.stream().collect(Collectors.toMap(ProductInfo::getArticle, Function.identity()));

        Assertions.assertEquals(new BigDecimal("1237"), map.get("216505461").getPrice());
        Assertions.assertEquals(new BigDecimal("1649"), map.get("122477595").getPrice());
        Assertions.assertEquals(BigDecimal.ZERO, map.get("161070370").getPrice());
    }

    private static final String JSON_RESPONSE_EXAMPLE = """
            {
              "state": 0,
              "payloadVersion": 2,
              "data": {
                "products": [
                  {
                    "id": 122477595,
                    "root": 201418498,
                    "kindId": 0,
                    "brand": "Резерв",
                    "brandId": 1103921,
                    "siteBrandId": 1113921,
                    "colors": [
                      {
                        "name": "серый",
                        "id": 8421504
                      }
                    ],
                    "subjectId": 2914,
                    "subjectParentId": 1590,
                    "name": "Котелок туристический 4 л для похода",
                    "supplier": "РЕЗЕРВ - Товары для Активного Отдыха",
                    "supplierId": 771921,
                    "supplierRating": 4.7,
                    "supplierFlags": 0,
                    "pics": 8,
                    "rating": 5,
                    "reviewRating": 4.9,
                    "feedbacks": 246,
                    "volume": 102,
                    "viewFlags": 0,
                    "promotions": [
                      63484,
                      92742,
                      96213,
                      118400,
                      133413,
                      141441,
                      152441,
                      160574,
                      162578,
                      172994,
                      178415,
                      183871,
                      184481
                    ],
                    "sizes": [
                      {
                        "name": "",
                        "origName": "0",
                        "rank": 0,
                        "optionId": 215630201,
                        "stocks": [
                          {
                            "wh": 206348,
                            "dtype": 4,
                            "qty": 1,
                            "priority": 40031,
                            "time1": 2,
                            "time2": 73
                          },
                          {
                            "wh": 130744,
                            "dtype": 4,
                            "qty": 34,
                            "priority": 49646,
                            "time1": 4,
                            "time2": 62
                          },
                          {
                            "wh": 686,
                            "dtype": 4,
                            "qty": 8,
                            "priority": 10570,
                            "time1": 2,
                            "time2": 196
                          },
                          {
                            "wh": 120762,
                            "dtype": 4,
                            "qty": 29,
                            "priority": 37305,
                            "time1": 8,
                            "time2": 75
                          },
                          {
                            "wh": 117986,
                            "dtype": 4,
                            "qty": 4,
                            "priority": 32341,
                            "time1": 3,
                            "time2": 101
                          }
                        ],
                        "time1": 4,
                        "time2": 62,
                        "wh": 130744,
                        "dtype": 4,
                        "price": {
                          "basic": 458200,
                          "product": 164900,
                          "total": 164900,
                          "logistics": 0,
                          "return": 0
                        },
                        "saleConditions": 134217728,
                        "payload": "Ldxxh8aO34f0x3FAsunl5UBuWd6Ki9814S0YmbYPQQ1mv2C0UrzBODUmEvaNVHceKAylD84lOn/FKqm/jTc"
                      }
                    ],
                    "totalQuantity": 76,
                    "time1": 4,
                    "time2": 62,
                    "wh": 130744,
                    "dtype": 4
                  },
                  {
                    "id": 161070370,
                    "root": 147714166,
                    "kindId": 0,
                    "brand": "Widesea",
                    "brandId": 880257,
                    "siteBrandId": 890257,
                    "colors": [
                      {
                        "name": "серый",
                        "id": 8421504
                      }
                    ],
                    "subjectId": 2914,
                    "subjectParentId": 1590,
                    "name": "Котелок с крышкой походный туристический 4л",
                    "supplier": "Всё для туризма",
                    "supplierId": 883252,
                    "supplierRating": 4.8,
                    "supplierFlags": 0,
                    "pics": 8,
                    "rating": 5,
                    "reviewRating": 4.8,
                    "feedbacks": 290,
                    "volume": 90,
                    "viewFlags": 0,
                    "promotions": [
                      63484
                    ],
                    "sizes": [
                      {
                        "name": "",
                        "origName": "0",
                        "rank": 0,
                        "optionId": 267667024,
                        "stocks": [
                         \s
                        ]
                      }
                    ],
                    "totalQuantity": 0
                  },
                  {
                    "id": 216505461,
                    "root": 201418498,
                    "kindId": 0,
                    "brand": "Резерв",
                    "brandId": 1103921,
                    "siteBrandId": 0,
                    "colors": [
                      {
                        "name": "серый",
                        "id": 8421504
                      },
                      {
                        "name": "зеленый",
                        "id": 32768
                      }
                    ],
                    "subjectId": 2914,
                    "subjectParentId": 1590,
                    "name": "Котелок походный туристический",
                    "supplier": "РЕЗЕРВ - Товары для Активного Отдыха",
                    "supplierId": 771921,
                    "supplierRating": 4.7,
                    "supplierFlags": 0,
                    "pics": 7,
                    "rating": 5,
                    "reviewRating": 4.9,
                    "feedbacks": 246,
                    "volume": 27,
                    "viewFlags": 16,
                    "promotions": [
                      63484,
                      92742,
                      96213,
                      118400,
                      133413,
                      152441,
                      160574,
                      162578,
                      172994,
                      178415,
                      183871,
                      184481
                    ],
                    "sizes": [
                      {
                        "name": "",
                        "origName": "0",
                        "rank": 0,
                        "optionId": 345119565,
                        "stocks": [
                          {
                            "wh": 686,
                            "dtype": 4,
                            "qty": 13,
                            "priority": 10570,
                            "time1": 2,
                            "time2": 196
                          },
                          {
                            "wh": 120762,
                            "dtype": 4,
                            "qty": 1,
                            "priority": 37305,
                            "time1": 8,
                            "time2": 75
                          },
                          {
                            "wh": 130744,
                            "dtype": 4,
                            "qty": 14,
                            "priority": 49646,
                            "time1": 4,
                            "time2": 62
                          }
                        ],
                        "time1": 4,
                        "time2": 62,
                        "wh": 130744,
                        "dtype": 4,
                        "price": {
                          "basic": 343800,
                          "product": 123700,
                          "total": 123700,
                          "logistics": 0,
                          "return": 0
                        },
                        "saleConditions": 134217728,
                        "payload": "SglymKQzO7aOyjezxJpeLYTUacGUlK4iC3AbyT9rwrcLYiQdKfZJXrLo5vifh4LRrmgNssrNoQyixvDsRuM"
                      }
                    ],
                    "totalQuantity": 28,
                    "time1": 4,
                    "time2": 62,
                    "wh": 130744,
                    "dtype": 4
                  }
                ]
              }
            }
            """;

}