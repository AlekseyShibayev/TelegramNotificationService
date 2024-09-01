package com.company.app.wildberries.common.get_products;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Getter
@Setter
@Accessors(chain = true)
public class ProductInfo {

    private String article;
    private String description;
    private BigDecimal price;
    private Integer rating;
    private Integer feedbacks;

}
