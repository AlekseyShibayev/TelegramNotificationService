package com.company.app.wildberries.knowledge.component;

import java.util.List;

import com.company.app.common.tool.json.JsonMapper;
import com.company.app.wildberries.knowledge.domain.entity.Supplier;
import com.company.app.wildberries.knowledge.domain.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class SupplierInitializer {

    @Value("classpath:wildberries_knowledge/init_supplier.json")
    private Resource resource;

    private final JsonMapper<Supplier> jsonTool;
    private final SupplierRepository supplierRepository;

    @EventListener({ContextRefreshedEvent.class})
    public void init() {
        List<Supplier> list = jsonTool.toJavaAsList(resource, Supplier.class);
        supplierRepository.saveAll(list);
    }

}
