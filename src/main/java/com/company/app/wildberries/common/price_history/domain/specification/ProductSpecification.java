package com.company.app.wildberries.common.price_history.domain.specification;

import java.util.Collection;

import com.company.app.wildberries.common.price_history.domain.entity.Product;
import com.company.app.wildberries.common.price_history.domain.entity.Product_;
import org.springframework.data.jpa.domain.Specification;


public class ProductSpecification {

    public static Specification<Product> articleIn(Collection<String> articles) {
        return (r, cq, cb) -> r.get(Product_.ARTICLE).in(articles);
    }

}