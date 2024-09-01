package com.company.app.wildberries.desire;

import com.company.app.wildberries.common.get_products.ProductInfo;
import com.company.app.wildberries.desire.domain.entity.Desire;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class DesireContext {

    private Desire desire;
    private ProductInfo productInfoDto;

}
