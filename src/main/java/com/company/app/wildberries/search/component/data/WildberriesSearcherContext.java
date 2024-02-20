package com.company.app.wildberries.search.component.data;

import com.company.app.wildberries.search.domain.entity.SearchData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WildberriesSearcherContext {

    private String chatName;
    private String footSize;
    private String dressSize;
    private String gender;
    private String supplier;
    private String greedIndex;

    public static WildberriesSearcherContext of(SearchData searchData) {
        WildberriesSearcherContext container = new WildberriesSearcherContext();
        BeanUtils.copyProperties(searchData, container);
        return container;
    }

    public static WildberriesSearcherContext of(WildberriesSearcherContext wildberriesSearcherContainer, SearchData searchData) {
        BeanUtils.copyProperties(searchData, wildberriesSearcherContainer);
        return wildberriesSearcherContainer;
    }
}
