package com.company.app.springboottest.application.exchangerate;

import com.company.app.exchange_rate.controller.ExchangeRateController;
import com.company.app.exchange_rate.domain.entity.ExchangeRate;
import com.company.app.exchange_rate.domain.repository.ExchangeRepository;
import com.company.app.exchange_rate.domain.service.api.ExchangeRateService;
import com.company.app.springboottest.application.SpringBootTestApplicationContext;
import com.company.app.telegram.binder.BinderContainer;
import com.company.app.telegram.binder.api.ExchangeRateBinder;
import com.company.app.telegram.domain.entity.Chat;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

class ExchangeRateEndToEndTest extends SpringBootTestApplicationContext {

	@Autowired
	ExchangeRateController exchangeRateController;
	@Autowired
	ExchangeRateBinder exchangeRateBinder;
	@Autowired
	ExchangeRepository exchangeRepository;
	@Autowired
	ExchangeRateService exchangeRateService;

	@BeforeEach
	void init() {
		exchangeRepository.deleteAll();
	}

	@Test
	void get_type_test() {
		Assertions.assertEquals("EX", exchangeRateBinder.getType());
	}

	@Test
	void controller_test() {
		ExchangeRate exchangeRate = exchangeRateController.extract().getBody();
		Assertions.assertNotNull(exchangeRate);
		Assertions.assertNotNull(exchangeRate.getCreationDate());
		Assertions.assertNotNull(exchangeRate.getAliexpressExchangeRate());
	}

	@Test
	void binder_fail_test() {
		BinderContainer binderContainer = createBinderContainer();
		Assertions.assertThrows(NoSuchElementException.class, () -> exchangeRateBinder.bind(binderContainer));
	}

	@Test
	void get_last_if_more_then_one_test() {
		ExchangeRate exchangeRate = ExchangeRate.builder().aliexpressExchangeRate("72").creationDate(new Date()).build();
		exchangeRateService.create(exchangeRate);
		ExchangeRate exchangeRate2 = ExchangeRate.builder().aliexpressExchangeRate("82").creationDate(new Date()).build();
		exchangeRateService.create(exchangeRate2);

		List<ExchangeRate> all = exchangeRepository.findAll();
		Assertions.assertEquals(2, all.size());

		ExchangeRate last = exchangeRateService.getLast();
		Assertions.assertEquals("82", last.getAliexpressExchangeRate());
	}

	@Test
	void binder_success_test() {
		ExchangeRate exchangeRate = exchangeRateController.extract().getBody();
		BinderContainer binderContainer = createBinderContainer();
		Assertions.assertDoesNotThrow(() -> exchangeRateBinder.bind(binderContainer));
	}

	private static BinderContainer createBinderContainer() {
		return BinderContainer.builder()
				.chat(Chat.builder().chatName(String.valueOf(653606407L)).build())
				.message("EX")
				.build();
	}
}
