package com.company.app.wildberries_searcher.data;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WildberriesSearcherContainer {

	String chatId;
	String footSize;
	String dressSize;
	String gender;
	String supplier;
}
