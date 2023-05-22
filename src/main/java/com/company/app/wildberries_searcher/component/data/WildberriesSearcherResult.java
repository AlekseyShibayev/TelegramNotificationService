package com.company.app.wildberries_searcher.component.data;

import com.company.app.wildberries_searcher.domain.dto.WildberriesLinkDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class WildberriesSearcherResult {

	boolean isSuccess;
	List<WildberriesLinkDto> wildberriesLinkDtoList;

	public boolean isNotSuccess() {
		return !isSuccess;
	}
}
