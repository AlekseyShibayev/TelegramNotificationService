package com.company.app.wildberries_desire_lot.domain.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LotDto {

    private String article;
    private String desiredPrice;
    private String discount;
}
