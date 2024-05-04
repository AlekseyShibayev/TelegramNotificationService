package com.company.app.wildberries.search.domain.mapper;

import com.company.app.wildberries.search.domain.dto.SearchDataDto;
import com.company.app.wildberries.search.domain.entity.SearchData;
import org.mapstruct.BeanMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring"
    , unmappedTargetPolicy = ReportingPolicy.WARN
    , injectionStrategy = InjectionStrategy.CONSTRUCTOR
    , imports = {}
    , uses = {}
)
public interface SearchDataDtoMapper {

//    @BeanMapping(ignoreByDefault = true)
    SearchDataDto mapToSearchDataDto(SearchData searchData);

}