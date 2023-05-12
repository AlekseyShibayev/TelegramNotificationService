package com.company.app.springboottest.application.wildberries.searcher;

import com.company.app.springboottest.application.ApplicationSpringBootTestContext;
import com.company.app.wildberries.component.searcher.api.WildberriesSearcher;
import com.company.app.wildberries.domain.dto.WildberriesLinkDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

class WildberriesSearcherEndToEndTest extends ApplicationSpringBootTestContext {

	@Autowired
	WildberriesSearcher wildberriesSearcher;

	@Test
	void temp_name() {
		List<WildberriesLinkDto> dtos = wildberriesSearcher.search("45", "male");
		Assertions.assertTrue(dtos.size() > 0);
	}
}
