package com.company.app.wildberries.desire.service.search;

import java.math.BigDecimal;
import java.util.List;

import com.company.app.configuration.SpringBootTestApplication;
import com.company.app.telegram.domain.entity.Chat;
import com.company.app.wildberries.desire.domain.entity.Desire;
import com.company.app.wildberries.desire.domain.entity.DesireLot;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


class WildberriesDesireLotUpdaterTest extends SpringBootTestApplication {

    @Autowired
    private WildberriesDesireLotUpdater wildberriesDesireLotUpdater;

    @Test
    void update_one_success_test() {
        Chat owner = chatRepository.findOwner();

        Desire desire = new Desire()
            .setChatName(owner.getChatName())
            .setArticle("133755774")
            .setPrice(new BigDecimal("700"));

        DesireLot before = new DesireLot()
            .setDesireList(List.of(desire))
            .setArticle("133755774")
            .setPrice(new BigDecimal("600"))
            .setDescription("desc before");
        desireLotRepository.save(before);

        DesireLot after = new DesireLot()
            .setDesireList(List.of(desire))
            .setArticle("133755774")
            .setPrice(new BigDecimal("601"))
            .setDescription("desc after");

        List<DesireLot> desireLots = wildberriesDesireLotUpdater.updateDesireLots(List.of(after));

        Assertions.assertEquals(desireLots.get(0).getPrice(), after.getPrice());
        Assertions.assertEquals(desireLots.get(0).getDescription(), after.getDescription());
    }

    @Test
    void update_two_success_test() {
        Chat owner = chatRepository.findOwner();

        Desire desire = new Desire()
            .setChatName(owner.getChatName())
            .setArticle("133755774")
            .setPrice(new BigDecimal("700"));

        DesireLot before = new DesireLot()
            .setDesireList(List.of(desire))
            .setArticle("133755774")
            .setPrice(new BigDecimal("600"))
            .setDescription("desc before");
        desireLotRepository.save(before);

        DesireLot after = new DesireLot()
            .setDesireList(List.of(desire))
            .setArticle("133755774")
            .setPrice(new BigDecimal("601"))
            .setDescription("desc after");

        DesireLot other = new DesireLot()
            .setDesireList(List.of(desire))
            .setArticle("100500")
            .setPrice(new BigDecimal("1"))
            .setDescription("other");

        List<DesireLot> desireLots = wildberriesDesireLotUpdater.updateDesireLots(List.of(after, other));

        Assertions.assertEquals(2, desireLots.size());
    }

}