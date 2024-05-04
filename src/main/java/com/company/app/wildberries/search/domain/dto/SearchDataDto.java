package com.company.app.wildberries.search.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;


@Getter
@Setter
@Accessors(chain = true)
@ToString
public class SearchDataDto {

    private String greedIndex;

    public static SearchDataDto empty() {
        return new SearchDataDto();
    }

}