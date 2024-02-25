package com.company.app.wildberries.search.service.filter;

import com.company.app.wildberries.common.model.VmProduct;
import com.company.app.wildberries.search.model.WbSearchContext;

import java.util.function.BiPredicate;


public interface WbFilter extends BiPredicate<VmProduct, WbSearchContext> {

}
