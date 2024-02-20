package com.company.app.common.tool.testEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

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
