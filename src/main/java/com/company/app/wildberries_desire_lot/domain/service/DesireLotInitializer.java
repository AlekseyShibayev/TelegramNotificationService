package com.company.app.wildberries_desire_lot.domain.service;

import com.company.app.core.tool.json.JsonTool;
import com.company.app.wildberries_desire_lot.domain.entity.Lot;
import com.company.app.wildberries_desire_lot.domain.repository.LotRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import javax.annotation.PostConstruct;
import java.util.List;

public class DesireLotInitializer {

    @Value("classpath:wildberries_desire_lot/init_lot.json")
    private Resource resource;

    @Autowired
    private LotRepository lotRepository;
    @Autowired
    private JsonTool<Lot> jsonTool;

    @SneakyThrows
    @PostConstruct
    public void init() {
        List<Lot> lots = jsonTool.toJavaAsList(resource, Lot.class);
        lotRepository.saveAll(lots);
    }

}
