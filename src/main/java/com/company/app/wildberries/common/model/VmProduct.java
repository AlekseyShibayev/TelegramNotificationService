package com.company.app.wildberries.common.model;

import java.util.List;

import com.company.app.core.util.Strings;
import com.company.app.wildberries.common.util.WildberriesUrlCreator;
import com.company.app.wildberries.search.domain.dto.LinkDto;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


@Getter
@Setter
@Accessors(chain = true)
public class VmProduct {

    private Integer id;
    private String name;
    private Integer salePriceU;
    private Integer rating;
    private Integer feedbacks;
    private List<VmSize> sizes;

    public LinkDto toLinkDto() {
        return new LinkDto()
                .setPrice(Strings.cutEnd(this.salePriceU.toString(), 2))
                .setLink(WildberriesUrlCreator.getUrlForResponse(this.id.toString()));
    }

}