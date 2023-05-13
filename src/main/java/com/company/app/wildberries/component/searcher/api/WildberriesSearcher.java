package com.company.app.wildberries.component.searcher.api;

import com.company.app.wildberries.component.searcher.WildberriesSearcherContainer;
import com.company.app.wildberries.domain.dto.WildberriesLinkDto;

import java.util.List;

public interface WildberriesSearcher {

	List<WildberriesLinkDto> search(WildberriesSearcherContainer wildberriesSearcherContainer);
}
