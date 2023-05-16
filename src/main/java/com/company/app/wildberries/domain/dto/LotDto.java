package com.company.app.wildberries.domain.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LotDto {

	private String article;
	private String desiredPrice;
	private String discount;
}
