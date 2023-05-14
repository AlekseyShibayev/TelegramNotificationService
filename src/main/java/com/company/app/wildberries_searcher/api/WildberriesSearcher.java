package com.company.app.wildberries_searcher.api;

import com.company.app.wildberries_desire_lot.domain.dto.WildberriesLinkDto;
import com.company.app.wildberries_searcher.data.WildberriesSearcherContainer;

import java.util.List;

public interface WildberriesSearcher {

	List<WildberriesLinkDto> search(WildberriesSearcherContainer wildberriesSearcherContainer);
}
