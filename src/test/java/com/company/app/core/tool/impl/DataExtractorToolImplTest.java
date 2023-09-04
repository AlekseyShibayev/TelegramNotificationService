package com.company.app.core.tool.impl;

import com.company.app.core.temp.tool.JsonSearcher;
import com.company.app.core.temp.tool.impl.DataExtractorToolImpl;
import lombok.SneakyThrows;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;
import java.util.Map;

class DataExtractorToolImplTest {

    private static final String FILE_NAME = "core/JsonObjectExample.html";

    private DataExtractorToolImpl dataExtractorTool;

    @BeforeEach
    public void init() {
        dataExtractorTool = new DataExtractorToolImpl();
    }

    @SneakyThrows
    @Test
    void jsonSearchTest() {
        String string = dataExtractorTool.getFileAsString(FILE_NAME);
        JSONObject jsonObject = new JSONObject(string);
        JsonSearcher jsonSearcher = new JsonSearcher();
        jsonSearcher.doRecursiveSearch(jsonObject, "activityAmount");

        JSONObject result = jsonSearcher.getResult();
        Object value = result.getDouble("value");
        Assertions.assertEquals("65.85", value.toString());
    }

    @Test
    void getFiles() {
        List<File> files = dataExtractorTool.getFiles("src/test/resources/core/forGetFilesTest");
        Assertions.assertEquals(3, files.size());
    }

    @Test
    void tool_can_get_properties_from_file_test() {
        Map<String, String> properties = dataExtractorTool.getProperties("core/tool/tool_test.properties");

        Assertions.assertEquals(2, properties.size());
        Assertions.assertEquals("2", properties.get("second.property"));
    }
}