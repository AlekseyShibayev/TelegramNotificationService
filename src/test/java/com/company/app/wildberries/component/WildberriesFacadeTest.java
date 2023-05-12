package com.company.app.wildberries.component;

import com.company.app.core.tool.api.JsonSerializationTool;
import com.company.app.springboottest.application.ApplicationSpringBootTestContext;
import com.company.app.wildberries.domain.entity.Lot;
import com.company.app.wildberries.domain.repository.LotRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.File;
import java.util.List;

class WildberriesFacadeTest extends ApplicationSpringBootTestContext {

	private static final String FILE_NAME = "src/test/resources/wildberries/lot.json";

	@Autowired
	private WildberriesFacade wildberriesFacade;
	@Autowired
	private JsonSerializationTool<Lot> jsonSerializationTool;
	@MockBean
	private LotRepository lotRepository;

	@Test
	void doMainLogicTest() {
		File file = new File(FILE_NAME);
		List<Lot> lots = jsonSerializationTool.load(file, Lot.class);
		Mockito.when(lotRepository.findAll()).thenReturn(lots);

		Assertions.assertEquals(1, wildberriesFacade.getDesiredLots().size());
	}
}