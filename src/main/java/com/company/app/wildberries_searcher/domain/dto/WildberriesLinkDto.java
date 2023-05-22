package com.company.app.wildberries_searcher.domain.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WildberriesLinkDto {

	private String price;
	private String link;

	public String toMessage() {
		return this.price + ": " + this.link;
	}
}
