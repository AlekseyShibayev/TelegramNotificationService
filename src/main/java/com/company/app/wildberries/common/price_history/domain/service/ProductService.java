package com.company.app.wildberries.common.price_history.domain.service;

import java.util.List;
import java.util.stream.Collectors;

import com.company.app.core.util.Strings;
import com.company.app.wildberries.common.price_history.domain.entity.Price;
import com.company.app.wildberries.common.price_history.domain.entity.Product;
import com.company.app.wildberries.common.price_history.domain.repository.ProductRepository;
import com.company.app.wildberries.common.price_history.model.VmPriceHistory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Product create(String article, List<VmPriceHistory> priceHistoryList) {
        Product persistedProduct = productRepository.save(new Product()
            .setArticle(article));

        var priceList = priceHistoryList.stream()
            .map(VmPriceHistory::getPrice)
            .map(vmPrice -> new Price()
                .setCost(Strings.cutEnd(vmPrice.getRub(), 2))
                .setProduct(persistedProduct)
            )
            .collect(Collectors.toList());

        persistedProduct.setPrice(priceList);
        return productRepository.save(persistedProduct);
    }

}