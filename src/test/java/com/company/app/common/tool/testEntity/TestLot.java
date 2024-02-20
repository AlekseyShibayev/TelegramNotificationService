package com.company.app.common.tool.testEntity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class TestLot {

    private Long id;
    private String name;
    private String price;
    private String discount;
    private ProductDescription productDescription;
    private List<ProductProperty> productPropertiesList;

}
