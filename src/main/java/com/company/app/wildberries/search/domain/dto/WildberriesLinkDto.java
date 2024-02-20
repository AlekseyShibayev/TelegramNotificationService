package com.company.app.wildberries.search.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WildberriesLinkDto {

    private String price;
    private String link;

    public String toMessage() {
        return this.price + ": " + this.link;
    }
}
