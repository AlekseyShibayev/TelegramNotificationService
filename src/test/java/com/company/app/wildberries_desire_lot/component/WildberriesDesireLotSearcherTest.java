package com.company.app.wildberries_desire_lot.component;

import com.company.app.core.SpringBootTestApplicationContext;
import com.company.app.core.temp.tool.json.JsonTool;
import com.company.app.wildberries_desire_lot.WildberriesDesireLotFacade;
import com.company.app.wildberries_desire_lot.domain.entity.Desire;
import com.company.app.wildberries_desire_lot.domain.entity.DesireLot;
import com.company.app.wildberries_desire_lot.domain.repository.DesireRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.File;
import java.util.List;

class WildberriesDesireLotSearcherTest extends SpringBootTestApplicationContext {

//    private static final String FILE_NAME = "src/test/resources/wildberries/lot.json";
//
//    @Autowired
//    private WildberriesDesireLotFacade wildberriesFacade;
//    @Autowired
//    private JsonTool<Desire> jsonTool;
//    @MockBean
//    private DesireRepository lotRepository;
//
//    @Test
//    void wildberries_can_getDesiredLots_test() {
//        File file = new File(FILE_NAME);
//        List<Desire> lots = jsonTool.toJavaAsList(file, Desire.class);
//        Mockito.when(lotRepository.findAll()).thenReturn(lots);
//
//        List<DesireLot> foundItems = wildberriesFacade.doDesireLotSearch();
//
//        Assertions.assertEquals(1, foundItems.size());
//        Assertions.assertEquals("43409221", foundItems.get(0).getArticle());
//    }

}