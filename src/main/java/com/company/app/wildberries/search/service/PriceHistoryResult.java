package com.company.app.wildberries.search.service;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.openqa.selenium.devtools.v121.network.model.RequestId;


@Getter
@Setter
@Accessors(chain = true)
public class PriceHistoryResult {

    private RequestId requestId;
    private String url;
    private String body;

}