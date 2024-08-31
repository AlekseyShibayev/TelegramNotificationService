package com.company.app.wildberries.common.model;

import com.company.app.wildberries.common.model.v2.VmPrice;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


@Getter
@Setter
@Accessors(chain = true)
public class VmSize {

    private String name;
    private VmPrice price;

}
