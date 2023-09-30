package com.company.app.wildberries_desire_lot.controller;

import com.company.app.wildberries_desire_lot.WildberriesDesireLotFacade;
import com.company.app.wildberries_desire_lot.domain.dto.FoundItemDto;
import com.company.app.wildberries_desire_lot.domain.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/wildberries")
public class WildberriesController {

//    private final WildberriesDesireLotFacade wildberriesFacade;
//
//    @GetMapping(value = "/getAllFoundItems", produces = "application/json")
//    public ResponseEntity<List<FoundItemDto>> getAllFoundItems() {
//        return ResponseEntity.ok(Mapper.of(wildberriesFacade.getAllFoundItems()));
//    }


}
