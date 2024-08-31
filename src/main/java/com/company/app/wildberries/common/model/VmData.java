package com.company.app.wildberries.common.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


@Getter
@Setter
@Accessors(chain = true)
public class VmData {

    private List<VmProduct> products;

}
