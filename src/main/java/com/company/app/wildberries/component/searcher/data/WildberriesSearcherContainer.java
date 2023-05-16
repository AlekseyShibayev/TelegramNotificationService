package com.company.app.wildberries.component.searcher.data;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WildberriesSearcherContainer {

	String chatName;
	String footSize;
	String dressSize;
	String gender;
	String supplier;
	String greedIndex;
}
