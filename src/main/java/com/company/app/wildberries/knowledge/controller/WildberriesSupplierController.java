package com.company.app.wildberries.knowledge.controller;

import com.company.app.wildberries.knowledge.domain.entity.Supplier;
import com.company.app.wildberries.knowledge.domain.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/wildberries/supplier")
public class WildberriesSupplierController {

    private final SupplierRepository supplierRepository;

    @GetMapping(value = "/getAll", produces = "application/json")
    public ResponseEntity<List<Supplier>> getAll() {
        return ResponseEntity.ok(supplierRepository.findAll());
    }

    @GetMapping(value = "/getBySupplierId", produces = "application/json")
    public ResponseEntity<Supplier> getBySupplierId(@RequestParam String supplierId) {
        return ResponseEntity.ok(supplierRepository.findBySupplierId(supplierId));
    }

}
