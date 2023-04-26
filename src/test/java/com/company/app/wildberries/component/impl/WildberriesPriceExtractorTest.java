package com.company.app.wildberries.component.impl;

import com.company.app.core.tool.impl.DataExtractorToolImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WildberriesPriceExtractorTest {

	private static final String FILE_NAME = "wildberries/response_example.json";

	private WildberriesPriceExtractorImpl wildberriesPriceExtractor;
	private DataExtractorToolImpl dataExtractorService;

	@BeforeEach
	public void init() {
		wildberriesPriceExtractor = new WildberriesPriceExtractorImpl();
		dataExtractorService = new DataExtractorToolImpl();
		wildberriesPriceExtractor.setDataExtractorService(dataExtractorService);
	}

	@Test
	void extract() {
		String fileAsString = dataExtractorService.getFileAsString(FILE_NAME);
		Assertions.assertEquals("109900", wildberriesPriceExtractor.extract(fileAsString, "43409221"));
	}
}