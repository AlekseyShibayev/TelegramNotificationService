package com.company.app.wildberries_desire_lot.controller;

import com.company.app.wildberries_desire_lot.component.WildberriesFacade;
import com.company.app.wildberries_searcher.data.WildberriesSearcherContainer;
import com.company.app.wildberries_desire_lot.domain.dto.FoundItemDto;
import com.company.app.wildberries_desire_lot.domain.dto.WildberriesLinkDto;
import com.company.app.wildberries_desire_lot.domain.util.FoundItemUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/wildberries")
public class WildberriesController {

	@Autowired
	WildberriesFacade wildberriesFacade;

	@GetMapping(value = "/getAllFoundItems", produces = "application/json")
	public ResponseEntity<List<FoundItemDto>> getAllFoundItems() {
		return ResponseEntity.ok(FoundItemUtil.of(wildberriesFacade.getAllFoundItems()));
	}

	@GetMapping(value = "/search", produces = "application/json")
	public ResponseEntity<List<WildberriesLinkDto>> search(@RequestParam WildberriesSearcherContainer wildberriesSearcherContainer) {
		return ResponseEntity.ok(wildberriesFacade.search(wildberriesSearcherContainer));
	}
}
