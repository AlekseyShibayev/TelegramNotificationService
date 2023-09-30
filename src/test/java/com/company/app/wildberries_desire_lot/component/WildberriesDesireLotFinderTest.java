package com.company.app.wildberries_desire_lot.component;

import com.company.app.core.SpringBootTestApplicationContext;
import com.company.app.wildberries_desire_lot.domain.entity.Desire;
import com.company.app.wildberries_desire_lot.domain.entity.DesireLot;
import com.company.app.wildberries_desire_lot.domain.repository.DesireLotRepository;
import com.company.app.wildberries_desire_lot.domain.repository.DesireRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

class WildberriesDesireLotFinderTest extends SpringBootTestApplicationContext {

    @Autowired
    private WildberriesDesireLotFinder wildberriesDesireLotFinder;
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

        List<Desire> desires = wildberriesDesireLotFinder.find();

        Assertions.assertEquals(1, desires.size());
    }

}