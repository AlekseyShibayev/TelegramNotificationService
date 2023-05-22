package com.company.app.wildberries_desire_lot.component.desire_lot.impl;

import com.company.app.core.tool.impl.DataExtractorToolImpl;
import com.company.app.core.tool.impl.JsonSerializationToolImpl;
import com.company.app.wildberries_desire_lot.component.common.data.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WildberriesPriceExtractorImplTest {

	private static final String FILE_NAME = "wildberries/response_example.json";

	private WildberriesPriceExtractorImpl wildberriesPriceExtractor;
	private DataExtractorToolImpl dataExtractorService;

	@BeforeEach
	public void init() {
		wildberriesPriceExtractor = new WildberriesPriceExtractorImpl();
		dataExtractorService = new DataExtractorToolImpl();
	}

	@Test
	void extract() {
		WildberriesPriceExtractorImpl wildberriesPriceExtractor = new WildberriesPriceExtractorImpl();
		DataExtractorToolImpl dataExtractorTool = new DataExtractorToolImpl();
		JsonSerializationToolImpl<Response> responseJsonSerializationTool = new JsonSerializationToolImpl<>();
		wildberriesPriceExtractor.setJsonSerializationTool(responseJsonSerializationTool);

		String fileAsString = dataExtractorTool.getFileAsString(FILE_NAME);
		String price = wildberriesPriceExtractor.extract(fileAsString, "43409221");

		Assertions.assertEquals("109900", price);
	}
}