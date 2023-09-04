package com.company.app.wildberries_knowledge.domain.service;

import com.company.app.core.temp.tool.json.JsonTool;
import com.company.app.wildberries_knowledge.domain.entity.Supplier;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplierInitializer {

    @Value("classpath:wildberries_knowledge/init_supplier.json")
    private Resource resource;

    private final JsonTool<Supplier> jsonTool;
    private final SupplierService supplierService;

    @EventListener({ContextRefreshedEvent.class})
    public void init() {
        List<Supplier> list = jsonTool.toJavaAsList(resource, Supplier.class);
        supplierService.saveAll(list);
    }

}
