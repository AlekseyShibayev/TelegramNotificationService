package com.company.app.wildberries.component.searcher.data;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WildberriesSearcherContainer {

	private String chatName;
	private String footSize;
	private String dressSize;
	private String gender;
	private String supplier;
	private String greedIndex;
}
