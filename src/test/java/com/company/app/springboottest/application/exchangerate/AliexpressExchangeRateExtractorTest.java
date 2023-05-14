package com.company.app.springboottest.application.exchangerate;

import com.company.app.exchange_rate.component.api.AliexpressExchangeRateExtractor;
import com.company.app.exchange_rate.component.api.AliexpressReceiver;
import com.company.app.springboottest.application.ApplicationSpringBootTestContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class AliexpressExchangeRateExtractorTest extends ApplicationSpringBootTestContext {

	@Autowired
	AliexpressReceiver aliexpressReceiver;
	@Autowired
	AliexpressExchangeRateExtractor aliexpressExchangeRateExtractor;

	@Test
	void aliexpress_receiver_can_get_html_response_test() {
		String htmlResponse = aliexpressReceiver.getHtmlResponse();
		Assertions.assertNotNull(htmlResponse);
		Assertions.assertFalse(htmlResponse.isEmpty());
	}

	@Test
	void aliexpress_exchange_rate_extractor_can_extract_html_response_test() {
		String aliexpressExchangeRate = aliexpressExchangeRateExtractor.extract();
		Assertions.assertNotNull(aliexpressExchangeRate);
		Assertions.assertFalse(aliexpressExchangeRate.isEmpty());
	}
}