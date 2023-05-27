package com.company.app.wildberries_searcher.component.data;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WildberriesSearcherResult {

	private boolean isSuccess;
	private String message;
}
