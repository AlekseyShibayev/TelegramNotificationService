package com.company.app.wildberries.common.model;

import java.util.List;

import lombok.*;
import lombok.experimental.Accessors;


@Getter
@Setter
@Accessors(chain = true)
public class VmData {

    List<VmProduct> products;

}
