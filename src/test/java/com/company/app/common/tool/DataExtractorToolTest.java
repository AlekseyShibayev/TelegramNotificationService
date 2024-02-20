package com.company.app.common.tool;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class DataExtractorToolTest {

    private DataExtractorTool dataExtractorTool;

    @BeforeEach
    public void init() {
        dataExtractorTool = new DataExtractorTool();
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