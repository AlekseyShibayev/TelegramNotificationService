package com.company.app.wildberries.common.model;

import java.util.List;

import com.company.app.core.util.Strings;
import com.company.app.wildberries.common.WildberriesUrlCreator;
import com.company.app.wildberries.search.domain.dto.WildberriesLinkDto;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


@Getter
@Setter
@Accessors(chain = true)
public class ResponseProducts {

    private Integer id;
    private String name;
    private Integer salePriceU;
    private String rating;
    private String feedbacks;
    private List<Size> sizes;

    public WildberriesLinkDto toLinkDto() {
        return WildberriesLinkDto.builder()
            .price(Strings.cutEnd(this.salePriceU.toString(), 2))
            .link(WildberriesUrlCreator.getUrlForResponse(this.id.toString()))
            .build();
    }

}