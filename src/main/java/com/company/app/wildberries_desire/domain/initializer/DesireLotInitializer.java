package com.company.app.wildberries_desire.domain.initializer;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.company.app.core.temp.tool.json.JsonTool;
import com.company.app.wildberries_desire.domain.entity.Desire;
import com.company.app.wildberries_desire.domain.repository.DesireRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class DesireLotInitializer {

    @Value("classpath:wildberries_desire_lot/init_desire.json")
    private Resource resource;

    private final DesireRepository desireRepository;
    private final JsonTool<Desire> jsonTool;

    @PostConstruct
    public void init() {
        List<Desire> currentDesires = desireRepository.findAll();
        Map<ImmutablePair<String, String>, Desire> chatNameVsDesire = currentDesires.stream()
            .collect(Collectors.toMap(desire ->
                new ImmutablePair<>(desire.getChatName(), desire.getArticle()), Function.identity()));

        List<Desire> lots = jsonTool.toJavaAsList(resource, Desire.class);
        List<Desire> newDesires = lots.stream()
            .filter(desire -> !chatNameVsDesire.containsKey(new ImmutablePair<>(desire.getChatName(), desire.getArticle())))
            .toList();

        desireRepository.saveAll(newDesires);
    }

}
