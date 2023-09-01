package com.company.app.core.tool;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@UtilityClass
public class Tool {

    @SneakyThrows
    public static String getFileAsString(String fileName) {
        String result;
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream(fileName)) {
            result = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        }
        return result;
    }

}
