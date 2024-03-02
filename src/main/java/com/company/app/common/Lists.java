package com.company.app.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public interface Lists {

    @SafeVarargs
    static <T> List<T> of(T... t) {
        return Arrays.stream(t).collect(Collectors.toCollection(ArrayList::new));
    }

}