package com.company.app.wildberries.search.domain.mapper;

import com.company.app.configuration.SpringBootTestApplication;
import com.company.app.wildberries.search.domain.dto.SearchDataDto;
import com.company.app.wildberries.search.domain.entity.SearchData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class SearchDataDtoMapperTest extends SpringBootTestApplication {

    @Autowired
    private SearchDataDtoMapper searchDataDtoMapper;

    @Test
    void test() {
        SearchData searchData = new SearchData()
                .setGreedIndex("1.40");

        SearchDataDto dto = searchDataDtoMapper.mapToSearchDataDto(searchData);

        Assertions.assertEquals("1.40", dto.getGreedIndex());
    }

}