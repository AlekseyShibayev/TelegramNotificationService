package com.company.app.wildberries_desire_lot.controller;

import com.company.app.wildberries_desire_lot.domain.dto.LotDto;
import com.company.app.wildberries_desire_lot.domain.entity.DesireLot;
import com.company.app.wildberries_desire_lot.domain.service.DesireLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Божественный и великолепный, RESTful controller.
 */
@RestController
@RequestMapping("/wildberries/lot")
public class LotController {

    @Autowired
    DesireLotService lotService;

    @PostMapping(value = "/", produces = "application/json")
    public ResponseEntity<Long> create(@RequestBody LotDto lotDto) {
        return ResponseEntity.ok(lotService.create(lotDto));
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<DesireLot> read(@PathVariable Long id) {
        return ResponseEntity.ok(lotService.read(id));
    }

    @PutMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Boolean> update(@PathVariable Long id,
                                          @RequestBody LotDto lotDto) {
        return ResponseEntity.ok(lotService.update(id, lotDto));
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        return ResponseEntity.ok(lotService.delete(id));
    }
}
