package com.company.app.wildberries_desire_lot.domain.service;

import com.company.app.core.tool.json.JsonTool;
import com.company.app.wildberries_desire_lot.domain.entity.DesireLot;
import com.company.app.wildberries_desire_lot.domain.repository.DesireLotRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DesireLotInitializer {

    @Value("classpath:wildberries_desire_lot/init_lot.json")
    private Resource resource;

    private final DesireLotRepository lotRepository;
    private final JsonTool<DesireLot> jsonTool;

    @SneakyThrows
    @PostConstruct
    public void init() {
        List<DesireLot> lots = jsonTool.toJavaAsList(resource, DesireLot.class);
        lotRepository.saveAll(lots);
    }

}
