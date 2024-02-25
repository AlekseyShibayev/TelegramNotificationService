package com.company.app.wildberries.search.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


@Getter
@Setter
@Accessors(chain = true)
public class WbSearchResult {

    private boolean isSuccess;
    private String message;

}
