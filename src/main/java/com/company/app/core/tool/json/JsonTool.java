package com.company.app.core.tool.json;

import org.springframework.core.io.Resource;

import java.io.File;
import java.io.InputStream;
import java.util.List;

public interface JsonTool<T> {

    void toJson(List<T> source, File target);

    String toJson(List<T> source);

    String toJson(Object source);

    List<T> toJavaAsList(File source, Class<T> type);

    List<T> toJavaAsList(String source, Class<T> type);

    List<T> toJavaAsList(InputStream source, Class<T> type);

    List<T> toJavaAsList(Resource source, Class<T> type);

    T toJavaAsObject(File source, Class<T> type);

    T toJavaAsObject(String source, Class<T> type);

    T toJavaAsObject(InputStream source, Class<T> type);

    T toJavaAsObject(Resource source, Class<T> type);

    T toJavaAsObject(String source, Class<T> type, MapperSettings settings);

}
