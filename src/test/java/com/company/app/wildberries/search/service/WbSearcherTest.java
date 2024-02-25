package com.company.app.wildberries.search.service;

import com.company.app.configuration.SpringBootTestApplication;
import com.company.app.telegram.domain.entity.Chat;
import com.company.app.wildberries.common.model.VmProduct;
import com.company.app.wildberries.common.model.VmSize;
import com.company.app.wildberries.search.domain.dto.LinkDto;
import com.company.app.wildberries.search.model.WbSearchContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

class WbSearcherTest extends SpringBootTestApplication {

    @Autowired
    private WbSearcher wbSearcher;
    @MockBean
    private WbSearcherApi wbSearcherApi;

    @Test
    void search_test() {
        Mockito.doCallRealMethod().when(captchaFighter).fight(Mockito.anyInt(), Mockito.anyInt());

        VmProduct testVmProduct = new VmProduct()
                .setId(9960002)
                .setName("Футболка")
                .setSalePriceU(100)
                .setRating(5)
                .setFeedbacks(69679)
                .setSizes(List.of(new VmSize().setName("50")));
        Mockito.when(wbSearcherApi.findProductsByWeb(Mockito.anyString())).thenReturn(List.of(testVmProduct));

        Chat owner = chatRepository.findOwner();

        WbSearchContext context = new WbSearchContext()
                .setChatName(owner.getChatName())
                .setGender("male")
                .setFootSize("45")
                .setDressSize("50")
                .setBrand("12845") //  Envy Lab
                .setGreedIndex("0.50");

        List<LinkDto> result = wbSearcher.search(context);

        Assertions.assertEquals(1, result.size());
    }

}