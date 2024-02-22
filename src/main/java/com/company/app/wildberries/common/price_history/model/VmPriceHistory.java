package com.company.app.wildberries.common.price_history.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


@Getter
@Setter
@Accessors(chain = true)
public class VmPriceHistory {

    private String dt;
    private VmPrice price;

}
