package com.company.app.wildberries_desire_lot.controller.executor;

import com.company.app.configuration.SpringBootTestApplicationContext;
import com.company.app.wildberries_desire_lot.controller.dto.FulfilledDesire;
import com.company.app.wildberries_desire_lot.domain.entity.Desire;
import com.company.app.wildberries_desire_lot.domain.entity.DesireLot;
import com.company.app.wildberries_desire_lot.domain.repository.DesireLotRepository;
import com.company.app.wildberries_desire_lot.domain.repository.DesireRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
class WildberriesDesireControllerExecutorTest extends SpringBootTestApplicationContext {

    @Autowired
    private WildberriesDesireControllerExecutor wildberriesDesireControllerExecutor;
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

        log.debug("*****");
        List<FulfilledDesire> desireDtoList = wildberriesDesireControllerExecutor.getFulfilledDesires("1");

        Assertions.assertEquals(1, desireDtoList.size());
        Assertions.assertEquals("999", desireDtoList.get(0).getArticle());

        List<FulfilledDesire> desireDtoList2 = wildberriesDesireControllerExecutor.getFulfilledDesires("2");
        Assertions.assertEquals(0, desireDtoList2.size());
    }

}