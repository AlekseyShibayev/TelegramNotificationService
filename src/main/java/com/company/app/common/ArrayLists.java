package com.company.app.common;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public interface ArrayLists {

    static <T> ArrayList<T> create(T... t) {
        return Stream.of(t).collect(Collectors.toCollection(ArrayList::new));
    }

}