package com.company.app.wildberries_desire.domain.repository;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import com.company.app.configuration.SpringBootTestApplication;
import com.company.app.wildberries_desire.domain.entity.Desire;
import com.company.app.wildberries_desire.domain.entity.DesireLot;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


class DesireRepositoryTest extends SpringBootTestApplication {

    @Autowired
    private DesireRepository desireRepository;
    @Autowired
    private DesireLotRepository desireLotRepository;

    @Test
    void findAllWithDesirePriceGreaterThenRealPrice_success_test() {
        Desire desire = new Desire()
            .setChatName("653606407")
            .setArticle("43409221")
            .setPrice(new BigDecimal("699"));
        desireRepository.save(desire);
        DesireLot desireLot = new DesireLot()
            .setArticle(desire.getArticle())
            .setPrice(new BigDecimal("500"))
            .setDesireList(Collections.singletonList(desire));
        desireLotRepository.save(desireLot);
        desire.setDesireLot(desireLot);
        desireRepository.save(desire);

        List<Desire> desires = desireRepository.findAllWithDesirePriceGreaterThenRealPrice();

        Assertions.assertEquals(1, desires.size());
    }

}