package com.company.app.wildberries.common.model;

import lombok.*;
import lombok.experimental.Accessors;


@Getter
@Setter
@Accessors(chain = true)
public class VmResponse {

    VmData data;

}
