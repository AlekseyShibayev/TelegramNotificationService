package com.company.app.wildberries_knowledge.domain.service;

import com.company.app.wildberries_knowledge.domain.entity.Supplier;
import com.company.app.wildberries_knowledge.domain.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplierService {

    private final SupplierRepository supplierRepository;

    public void saveAll(List<Supplier> list) {
        supplierRepository.saveAll(list);
    }

    public List<Supplier> getAll() {
        return supplierRepository.findAll();
    }

    public Supplier getBySupplierId(String supplierId) {
        return supplierRepository.findBySupplierId(supplierId);
    }

}
