package com.company.app.wildberries.search.domain.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


@Getter
@Setter
@Accessors(chain = true)
@EqualsAndHashCode
public class LinkDto {

    private String price;
    private String link;

    public String toMessage() {
        return this.price + ": " + this.link;
    }

}