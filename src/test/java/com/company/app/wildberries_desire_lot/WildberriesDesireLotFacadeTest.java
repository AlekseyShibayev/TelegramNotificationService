package com.company.app.wildberries_desire_lot;

import com.company.app.core.tool.json.JsonTool;
import com.company.app.springboottest.application.SpringBootTestApplicationContext;
import com.company.app.wildberries_desire_lot.domain.entity.DesireLot;
import com.company.app.wildberries_desire_lot.domain.repository.DesireLotRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.File;
import java.util.List;

class WildberriesDesireLotFacadeTest extends SpringBootTestApplicationContext {

    private static final String FILE_NAME = "src/test/resources/wildberries/lot.json";

    @Autowired
    private WildberriesDesireLotFacade wildberriesFacade;
    @Autowired
    private JsonTool<DesireLot> jsonTool;
    @MockBean
    private DesireLotRepository lotRepository;

    @Test
    void wildberries_can_getDesiredLots_test() {
        File file = new File(FILE_NAME);
        List<DesireLot> lots = jsonTool.toJavaAsList(file, DesireLot.class);
        Mockito.doNothing().when(captchaFighter).fight(Mockito.anyInt(), Mockito.anyInt());
        Mockito.when(lotRepository.findAll()).thenReturn(lots);

        int size = wildberriesFacade.getDesiredLots().size();

        Assertions.assertEquals(1, size);
    }

}