package com.company.app.wildberries_desire_lot.domain.mapper;

import com.company.app.wildberries_desire_lot.component.WildberriesDesireLotUrlCreator;
import com.company.app.wildberries_desire_lot.domain.dto.FoundItemDto;
import com.company.app.wildberries_desire_lot.domain.entity.DesireLot;
import lombok.experimental.UtilityClass;
import org.springframework.beans.BeanUtils;

import java.util.List;

@UtilityClass
public class Mapper {

    public static FoundItemDto of(DesireLot foundItem) {
        FoundItemDto foundItemDto = new FoundItemDto();
        BeanUtils.copyProperties(foundItem, foundItemDto);
        return foundItemDto;
    }

    public static List<FoundItemDto> of(List<DesireLot> foundItems) {
        List<FoundItemDto> result = foundItems.stream()
                .map(Mapper::of)
                .toList();
        result.forEach(foundItemDto -> foundItemDto.setLink(WildberriesDesireLotUrlCreator.getUrlForResponse(foundItemDto.getArticle())));
        return result;
    }

}
