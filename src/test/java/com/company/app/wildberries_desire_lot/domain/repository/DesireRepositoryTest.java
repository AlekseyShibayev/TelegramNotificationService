package com.company.app.wildberries_desire_lot.domain.repository;

import com.company.app.core.SpringBootTestApplicationContext;
import com.company.app.wildberries_desire_lot.domain.entity.Desire;
import com.company.app.wildberries_desire_lot.domain.entity.DesireLot;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

class DesireRepositoryTest extends SpringBootTestApplicationContext {

    @Autowired
    private DesireRepository desireRepository;
    @Autowired
    private DesireLotRepository desireLotRepository;

    @Test
    void test() {
        Desire desire = new Desire()
                .setChatName("653606407")
                .setArticle("43409221")
                .setPrice(new BigDecimal("699"))
                .setDiscount("0.15");
        desireRepository.save(desire);
        DesireLot desireLot = new DesireLot()
                .setArticle(desire.getArticle())
                .setPrice(new BigDecimal("500"))
                .setDesireList(Collections.singletonList(desire));
        desireLotRepository.save(desireLot);
        desire.setDesireLot(desireLot);
        desireRepository.save(desire);

        List<Desire> desires = desireRepository.findWithDesirePriceGreaterThenRealPrice();

        Assertions.assertEquals(1, desires.size());
    }

}