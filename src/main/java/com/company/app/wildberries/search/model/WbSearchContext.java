package com.company.app.wildberries.search.model;

import com.company.app.wildberries.search.domain.entity.SearchData;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;


@Getter
@Setter
@Accessors(chain = true)
public class WbSearchContext {

    private String chatName;
    private String footSize;
    private String dressSize;
    private String gender;
    private String brand;
    private String greedIndex;

    public static WbSearchContext of(SearchData searchData) {
        WbSearchContext context = new WbSearchContext();
        BeanUtils.copyProperties(searchData, context);
        return context;
    }

    public static WbSearchContext of(WbSearchContext context, SearchData searchData) {
        BeanUtils.copyProperties(searchData, context);
        return context;
    }

}