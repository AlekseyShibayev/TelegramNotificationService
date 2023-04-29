package com.company.app.wildberries.util;

import com.company.app.wildberries.dto.FoundItemDto;
import com.company.app.wildberries.entity.FoundItem;
import lombok.experimental.UtilityClass;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class FoundItemUtil {

	public static FoundItemDto of(FoundItem foundItem) {
		FoundItemDto foundItemDto = new FoundItemDto();
		BeanUtils.copyProperties(foundItem, foundItemDto);
		return foundItemDto;
	}

	public static List<FoundItemDto> of(List<FoundItem> foundItems) {
		List<FoundItemDto> result = foundItems.stream()
				.map(FoundItemUtil::of)
				.collect(Collectors.toList());
		result.forEach(foundItemDto -> foundItemDto.setLink(WBUtils.getUrlForResponse(foundItemDto.getArticle())));
		return result;
	}
}
