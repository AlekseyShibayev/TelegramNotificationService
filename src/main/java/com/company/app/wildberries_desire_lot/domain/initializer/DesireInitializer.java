package com.company.app.wildberries_desire_lot.domain.initializer;

import com.company.app.core.temp.tool.json.JsonTool;
import com.company.app.wildberries_desire_lot.domain.entity.Desire;
import com.company.app.wildberries_desire_lot.domain.repository.DesireRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DesireInitializer {

    @Value("classpath:wildberries_desire_lot/init_desire.json")
    private Resource resource;

    private final DesireRepository desireRepository;
    private final JsonTool<Desire> jsonTool;

    @SneakyThrows
    @PostConstruct
    public void init() {
        List<Desire> lots = jsonTool.toJavaAsList(resource, Desire.class);
        desireRepository.saveAll(lots);
    }

}
