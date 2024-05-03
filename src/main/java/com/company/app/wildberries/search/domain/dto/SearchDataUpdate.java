package com.company.app.wildberries.search.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class SearchDataUpdate {

    private String chatName;
    private String greedIndex;

}