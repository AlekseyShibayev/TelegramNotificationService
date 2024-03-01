package com.company.app.wildberries.common.price_history.domain.specification;

import java.util.Collection;

import com.company.app.wildberries.common.price_history.domain.entity.Product;
import com.company.app.wildberries.common.price_history.domain.entity.Product_;
import org.springframework.data.jpa.domain.Specification;


public interface ProductSpecification {

    static Specification<Product> articleIs(String article) {
        return (r, cq, cb) -> cb.equal(r.get(Product_.ARTICLE), article);
    }

    static Specification<Product> articleIn(Collection<String> articles) {
        return (r, cq, cb) -> r.get(Product_.ARTICLE).in(articles);
    }

}