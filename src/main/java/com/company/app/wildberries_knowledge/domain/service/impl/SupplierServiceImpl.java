package com.company.app.wildberries_knowledge.domain.service.impl;

import com.company.app.wildberries_knowledge.domain.entity.Supplier;
import com.company.app.wildberries_knowledge.domain.repository.SupplierRepository;
import com.company.app.wildberries_knowledge.domain.service.api.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    @Transactional
    @Override
    public void saveAll(List<Supplier> list) {
        supplierRepository.saveAll(list);
    }

    @Transactional
    @Override
    public List<Supplier> getAll() {
        return supplierRepository.findAll();
    }

    @Transactional
    @Override
    public Supplier getBySupplierId(String supplierId) {
        return supplierRepository.findBySupplierId(supplierId);
    }
}
