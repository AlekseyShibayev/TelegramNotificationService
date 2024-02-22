package com.company.app.wildberries.common.price_history.model;

import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


@Getter
@Setter
@Accessors(chain = true)
public class VmPrice {

    @JsonSetter("RUB")
    private String rub;

}
