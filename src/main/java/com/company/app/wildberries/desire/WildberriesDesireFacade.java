package com.company.app.wildberries.desire;

import java.util.List;

import com.company.app.wildberries.desire.domain.dto.FulfilledDesire;
import com.company.app.wildberries.desire.domain.repository.WildberriesFulfilledDesireRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class WildberriesDesireFacade {

    private final WildberriesFulfilledDesireRepository wildberriesFulfilledDesireRepository;

    public List<FulfilledDesire> getFulfilledDesires(String chatName) {
        return wildberriesFulfilledDesireRepository.findAllByChatName(chatName);
    }

}