package com.company.app.core.temp.data;

import com.company.app.core.util.Strings;
import com.company.app.wildberries_desire_lot.component.data.WildberriesDesireLotUrlCreator;
import com.company.app.wildberries_searcher.domain.dto.WildberriesLinkDto;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

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
                .link(WildberriesDesireLotUrlCreator.getUrlForResponse(this.id.toString()))
                .build();
    }

}