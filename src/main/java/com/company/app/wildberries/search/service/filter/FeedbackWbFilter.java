package com.company.app.wildberries.search.service.filter;

import com.company.app.wildberries.common.model.VmProduct;
import com.company.app.wildberries.search.model.WbSearchContext;
import org.springframework.stereotype.Service;


@Service
public class FeedbackWbFilter implements WbFilter {

    @Override
    public boolean test(VmProduct product, WbSearchContext context) {
        return product.getFeedbacks() >= 10;
    }

}