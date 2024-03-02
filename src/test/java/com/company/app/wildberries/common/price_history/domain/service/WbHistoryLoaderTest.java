package com.company.app.wildberries.common.price_history.domain.service;

import java.util.List;
import java.util.Optional;

import com.company.app.common.entity_finder.model.PersistenceContext;
import com.company.app.common.tool.HttpService;
import com.company.app.configuration.SpringBootTestApplication;
import com.company.app.core.util.Collections;
import com.company.app.wildberries.common.price_history.domain.entity.Price;
import com.company.app.wildberries.common.price_history.domain.entity.Product;
import com.company.app.wildberries.common.price_history.domain.entity.Product_;
import com.company.app.wildberries.common.price_history.service.WbHistoryLoader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;


class WbHistoryLoaderTest extends SpringBootTestApplication {

    @Autowired
    private WbHistoryLoader wbHistoryLoader;
    @MockBean
    protected HttpService httpService;

    @Test
    void can_load_price_history_and_change_old_history_for_new_history() {
        Product product = transactionTemplate.execute(transactionStatus -> {
            Product saved = productRepository.save(new Product()
                .setArticle("180189153"));

            saved.setPrice(Collections.list(priceRepository.save(new Price().setCost("200").setProduct(saved))
                , priceRepository.save(new Price().setCost("300").setProduct(saved))));
            return productRepository.save(saved);
        });

        String json = """
                      [{"dt":1708214400,"price":{"RUB":631900}}]""";
        Mockito.when(httpService.get(Mockito.any())).thenReturn(Optional.of(json));

        transactionTemplate.executeWithoutResult(transactionStatus ->
            wbHistoryLoader.loadPriceHistory(List.of(product))
        );

        List<Product> all = entityFinder.findAll(new PersistenceContext<>(Product.class)
            .with(Product_.PRICE));

        Assertions.assertEquals(1, all.size());
        Product extracted = all.get(0);
        Assertions.assertEquals("180189153", extracted.getArticle());
        Assertions.assertEquals(1, extracted.getPrice().size());
        Assertions.assertEquals("6319", extracted.getPrice().get(0).getCost());
    }

}