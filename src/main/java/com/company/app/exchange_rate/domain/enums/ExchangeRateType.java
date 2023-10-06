package com.company.app.exchange_rate.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExchangeRateType {

    ALIEXPRESS("ALIEXPRESS");

    private final String type;

}
