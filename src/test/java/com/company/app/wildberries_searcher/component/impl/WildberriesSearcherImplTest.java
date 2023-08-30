package com.company.app.wildberries_searcher.component.impl;

import com.company.app.springboottest.application.SpringBootTestApplicationContext;
import com.company.app.wildberries_searcher.component.api.WildberriesSearcher;
import com.company.app.wildberries_searcher.component.data.WildberriesSearcherContext;
import com.company.app.wildberries_searcher.domain.dto.WildberriesLinkDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

class WildberriesSearcherImplTest extends SpringBootTestApplicationContext {

    @Autowired
    private WildberriesSearcher wildberriesSearcher;

//    @Test
//    void test_1() {
//        WildberriesSearcherContext context = WildberriesSearcherContext.builder()
//                .chatName("653606407")
//                .gender("male")
//                .footSize("45")
//                .dressSize("46;48")
//                .supplier("5472")
//                .greedIndex("1.00")
//                .build();
//
//        List<WildberriesLinkDto> result = wildberriesSearcher.search(context);
//
//        Assertions.assertEquals(1, result.size());
//    }

}