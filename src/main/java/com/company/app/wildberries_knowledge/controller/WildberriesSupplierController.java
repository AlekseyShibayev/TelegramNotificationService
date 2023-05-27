package com.company.app.wildberries_knowledge.controller;

import com.company.app.wildberries_knowledge.domain.entity.Supplier;
import com.company.app.wildberries_knowledge.domain.service.api.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/wildberries/supplier")
public class WildberriesSupplierController {

	@Autowired
	private SupplierService supplierService;

	@GetMapping(value = "/getAll", produces = "application/json")
	public ResponseEntity<List<Supplier>> getAll() {
		return ResponseEntity.ok(supplierService.getAll());
	}

	@GetMapping(value = "/getBySupplierId", produces = "application/json")
	public ResponseEntity<Supplier> getBySupplierId(@RequestParam String supplierId) {
		return ResponseEntity.ok(supplierService.getBySupplierId(supplierId));
	}
}
