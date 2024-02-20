package com.company.app.common.tool;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Stream;

import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DataExtractorTool {

    @SneakyThrows
    public String getFileAsString(String fileName) {
        String result;
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream(fileName)) {
            result = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        }
        return result;
    }

    @SneakyThrows
    public List<File> getFiles(String packageName) {
        try (Stream<Path> walk = Files.walk(Paths.get(packageName))) {
            return walk.filter(Files::isRegularFile)
                .map(Path::toFile)
                .toList();
        }
    }

    @SneakyThrows
    public Map<String, String> getProperties(String fileName) {
        Properties properties = new Properties();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try (InputStream stream = classLoader.getResourceAsStream(fileName)) {
            properties.load(stream);
        }
        return Maps.fromProperties(properties);
    }

    @SneakyThrows
    public String getHtmlResponse(String urlName) {
        StringBuilder response = new StringBuilder();
        URL url = new URL(urlName);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            fillResponse(connection, response);
        }
        return response.toString();
    }

    private void fillResponse(HttpURLConnection connection, StringBuilder response) throws IOException {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }
    }

}