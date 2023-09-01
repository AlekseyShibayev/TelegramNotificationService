package com.company.app.core.tool.json;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import lombok.SneakyThrows;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.InputStream;
import java.util.List;

@Component
public class JsonToolImpl<T> implements JsonTool<T> {

    private static final ObjectMapper mapper = new ObjectMapper();

    @SneakyThrows
    @Override
    public void toJson(List<T> source, File target) {
        mapper.writeValue(target, source.toArray());
    }

    @SneakyThrows
    @Override
    public String toJson(List<T> source) {
        return mapper.writeValueAsString(source);
    }

    @SneakyThrows
    @Override
    public String toJson(Object source) {
        return mapper.writeValueAsString(source);
    }

    @SneakyThrows
    @Override
    public List<T> toJavaAsList(File source, Class<T> type) {
        return mapper.readValue(source, getCollectionType(type));
    }

    @SneakyThrows
    @Override
    public List<T> toJavaAsList(String source, Class<T> type) {
        return mapper.readValue(source, getCollectionType(type));
    }

    @SneakyThrows
    @Override
    public List<T> toJavaAsList(InputStream source, Class<T> type) {
        return mapper.readValue(source, getCollectionType(type));
    }

    @SneakyThrows
    @Override
    public List<T> toJavaAsList(Resource source, Class<T> type) {
        List<T> list;
        try (InputStream inputStream = source.getInputStream()) {
            list = this.toJavaAsList(inputStream, type);
        }
        return list;
    }

    @Override
    public T toJavaAsObject(File source, Class<T> type) {
        throw new UnsupportedOperationException();
    }

    @SneakyThrows
    @Override
    public T toJavaAsObject(String source, Class<T> type) {
        return mapper.readValue(source, type);
    }

    @Override
    public T toJavaAsObject(InputStream source, Class<T> type) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T toJavaAsObject(Resource source, Class<T> type) {
        throw new UnsupportedOperationException();
    }

    @SneakyThrows
    @Override
    public T toJavaAsObject(String source, Class<T> type, MapperSettings settings) {
        ObjectMapper copy = copyAndConfigure(settings);
        return copy.readValue(source, type);
    }

    private CollectionType getCollectionType(Class<T> type) {
        return mapper.getTypeFactory().constructCollectionType(List.class, type);
    }
    private ObjectMapper copyAndConfigure(MapperSettings settings) {
        ObjectMapper copy = mapper.copy();
        copy.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, settings.isFailOnUnknownProperties());
        return copy;
    }

}
