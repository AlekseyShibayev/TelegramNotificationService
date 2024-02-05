package com.company.app.wildberries_desire_lot.domain.initializer;

import com.company.app.core.temp.tool.json.JsonTool;
import com.company.app.telegram.domain.entity.Mode;
import com.company.app.wildberries_desire_lot.domain.entity.Desire;
import com.company.app.wildberries_desire_lot.domain.repository.DesireRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DesireLotDomainInitializer {

    @Value("classpath:wildberries_desire_lot/init_desire.json")
    private Resource resource;

    private final DesireRepository desireRepository;
    private final JsonTool<Desire> jsonTool;

    @SneakyThrows
    @PostConstruct
    public void init() {
        List<Desire> currentDesires = desireRepository.findAll();
        Map<ChatNameAndArticle, Desire> chatNameVsDesire = currentDesires.stream()
                .collect(Collectors.toMap(desire -> new ChatNameAndArticle(desire.getChatName(), desire.getArticle()), Function.identity()));

        List<Desire> lots = jsonTool.toJavaAsList(resource, Desire.class);
        List<Desire> newDesires = lots.stream()
                .filter(desire -> !chatNameVsDesire.containsKey(new ChatNameAndArticle(desire.getChatName(), desire.getArticle())))
                .toList();

        desireRepository.saveAll(newDesires);
    }

}
