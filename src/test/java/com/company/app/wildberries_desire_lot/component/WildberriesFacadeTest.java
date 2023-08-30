package com.company.app.wildberries_desire_lot.component;

import com.company.app.core.tool.api.CaptchaFighter;
import com.company.app.core.tool.json.JsonTool;
import com.company.app.springboottest.application.SpringBootTestApplicationContext;
import com.company.app.wildberries_desire_lot.domain.entity.Lot;
import com.company.app.wildberries_desire_lot.domain.repository.LotRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.File;
import java.util.List;

class WildberriesFacadeTest extends SpringBootTestApplicationContext {

//    private static final String FILE_NAME = "src/test/resources/wildberries/lot.json";
//
//    @Autowired
//    private WildberriesFacade wildberriesFacade;
//    @Autowired
//    private JsonTool<Lot> jsonTool;
//    @MockBean
//    private LotRepository lotRepository;
//    @MockBean
//    private CaptchaFighter captchaFighter;

//    @Test
//    void wildberries_can_getDesiredLots_test() {
//        File file = new File(FILE_NAME);
//        List<Lot> lots = jsonTool.toJavaAsList(file, Lot.class);
//        Mockito.doNothing().when(captchaFighter).fight(Mockito.anyInt(), Mockito.anyInt());
//        Mockito.when(lotRepository.findAll()).thenReturn(lots);
//
//        int size = wildberriesFacade.getDesiredLots().size();
//
//        Assertions.assertEquals(1, size);
//    }

}