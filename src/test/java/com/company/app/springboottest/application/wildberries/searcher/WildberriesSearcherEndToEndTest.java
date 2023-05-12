package com.company.app.springboottest.application.wildberries.searcher;

import com.company.app.springboottest.application.ApplicationSpringBootTestContext;
import com.company.app.wildberries.component.searcher.api.WildberriesSearcher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class WildberriesSearcherEndToEndTest extends ApplicationSpringBootTestContext {

	@Autowired
	WildberriesSearcher wildberriesSearcher;

	@Test
	void temp_name() {
		wildberriesSearcher.search();
	}
}
