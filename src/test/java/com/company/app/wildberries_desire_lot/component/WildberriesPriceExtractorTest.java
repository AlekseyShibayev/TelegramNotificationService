package com.company.app.wildberries_desire_lot.component;

import com.company.app.core.SpringBootTestApplicationContext;
import com.company.app.core.tool.api.DataExtractorTool;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class WildberriesPriceExtractorTest extends SpringBootTestApplicationContext {

    private static final String FILE_NAME = "wildberries/response_example.json";

    @Autowired
    private WildberriesDesireLotPriceExtractor wildberriesPriceExtractor;
    @Autowired
    private DataExtractorTool dataExtractorTool;

    @Test
    void extract() {
        String fileAsString = dataExtractorTool.getFileAsString(FILE_NAME);

        String price = wildberriesPriceExtractor.extract(fileAsString, "43409221");

        Assertions.assertEquals("109900", price);
    }

}