package com.company.app.wildberries_knowledge.domain.service.impl;

import com.company.app.core.tool.api.JsonSerializationTool;
import com.company.app.wildberries_knowledge.domain.entity.Supplier;
import com.company.app.wildberries_knowledge.domain.service.api.InitialRegistry;
import com.company.app.wildberries_knowledge.domain.service.api.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InitialRegistryImpl implements InitialRegistry {

    @Value("classpath:wildberries/init_supplier.json")
    private Resource resource;

    @Autowired
    private JsonSerializationTool<Supplier> jsonSerializationTool;
    @Autowired
    private SupplierService supplierService;

    @EventListener({ContextRefreshedEvent.class})
    @Override
    public void init() {
        List<Supplier> list = jsonSerializationTool.load(resource, Supplier.class);
        supplierService.saveAll(list);
    }
}
