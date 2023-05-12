package com.company.app.wildberries.component.data;

import com.company.app.wildberries.component.util.WBUtils;
import com.company.app.wildberries.domain.dto.WildberriesLinkDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseProducts {

	private Integer id;
	private String name;
	private Integer salePriceU;
	private List<Size> sizes;

	public WildberriesLinkDto to() {
		String price = StringUtils.removeEnd(this.salePriceU.toString(), "00");
		return WildberriesLinkDto.builder()
				.price(price)
				.link(WBUtils.getUrlForResponse(this.id.toString()))
				.build();
	}
}
