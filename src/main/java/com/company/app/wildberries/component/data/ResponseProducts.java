package com.company.app.wildberries.component.data;

import com.company.app.wildberries.component.util.WBUtils;
import com.company.app.wildberries.domain.dto.WildberriesLinkDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseProducts {

	Integer id;
	String name;
	Integer salePriceU;
	List<Size> sizes;

	public WildberriesLinkDto to() {
		return WildberriesLinkDto.builder()
				.link(WBUtils.getUrlForResponse(id.toString()))
				.build();
	}
}
