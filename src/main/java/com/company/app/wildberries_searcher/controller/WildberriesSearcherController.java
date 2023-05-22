package com.company.app.wildberries_searcher.controller;

import com.company.app.wildberries_searcher.component.WildberriesSearcherFacade;
import com.company.app.wildberries_searcher.component.data.WildberriesSearcherContainer;
import com.company.app.wildberries_searcher.component.data.WildberriesSearcherResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wildberries_searcher")
public class WildberriesSearcherController {

	@Autowired
	private WildberriesSearcherFacade wildberriesSearcherFacade;

	@GetMapping(value = "/search", produces = "application/json")
	public ResponseEntity<WildberriesSearcherResult> search(@RequestParam WildberriesSearcherContainer wildberriesSearcherContainer) {
		return ResponseEntity.ok(wildberriesSearcherFacade.search(wildberriesSearcherContainer));
	}
}
