package com.company.app.wildberries_desire.domain.repository;

import java.math.BigDecimal;
import java.util.List;

import com.company.app.configuration.SpringBootTestApplication;
import com.company.app.wildberries_desire.domain.dto.FulfilledDesire;
import com.company.app.wildberries_desire.domain.entity.Desire;
import com.company.app.wildberries_desire.domain.entity.DesireLot;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;


class WildberriesFulfilledDesireRepositoryTest extends SpringBootTestApplication {

    @Autowired
    private WildberriesFulfilledDesireRepository wildberriesFulfilledDesireRepository;
    @Autowired
    private DesireRepository desireRepository;
    @Autowired
    private DesireLotRepository desireLotRepository;

    @Test
    void test() {
        transactionTemplate.executeWithoutResult(transactionStatus -> {
            DesireLot desireLot = new DesireLot()
                .setArticle("999")
                .setPrice(new BigDecimal("500"));
            desireLotRepository.save(desireLot);

            Desire desire = new Desire()
                .setChatName("1")
                .setArticle(desireLot.getArticle())
                .setPrice(new BigDecimal("699"))
                .setDesireLot(desireLot);
            desireRepository.save(desire);

            Desire desire2 = new Desire()
                .setChatName("2")
                .setArticle(desireLot.getArticle())
                .setPrice(new BigDecimal("300"))
                .setDesireLot(desireLot);
            desireRepository.save(desire2);
        });

        List<FulfilledDesire> desireDtoList = wildberriesFulfilledDesireRepository.findAllByChatName("1");

        Assertions.assertEquals(1, desireDtoList.size());
        Assertions.assertEquals("999", desireDtoList.get(0).getArticle());

        List<FulfilledDesire> desireDtoList2 = wildberriesFulfilledDesireRepository.findAllByChatName("2");
        Assertions.assertEquals(0, desireDtoList2.size());
    }

}