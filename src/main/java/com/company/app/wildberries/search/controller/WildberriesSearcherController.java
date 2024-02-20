package com.company.app.wildberries.search.controller;

import com.company.app.wildberries.search.WildberriesSearcherFacade;
import com.company.app.wildberries.search.component.data.WildberriesSearcherContext;
import com.company.app.wildberries.search.component.data.WildberriesSearcherResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/wildberries/searcher")
public class WildberriesSearcherController {

    private final WildberriesSearcherFacade wildberriesSearcherFacade;

    @GetMapping(value = "/search", produces = "application/json")
    public ResponseEntity<WildberriesSearcherResult> search(@RequestParam WildberriesSearcherContext wildberriesSearcherContainer) {
        return ResponseEntity.ok(wildberriesSearcherFacade.search(wildberriesSearcherContainer));
    }

}
