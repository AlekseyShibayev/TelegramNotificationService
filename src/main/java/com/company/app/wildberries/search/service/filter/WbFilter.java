package com.company.app.wildberries.search.service.filter;

import java.util.function.BiPredicate;

import com.company.app.wildberries.common.model.VmProduct;
import com.company.app.wildberries.search.model.WbSearchContext;


public interface WbFilter extends BiPredicate<VmProduct, WbSearchContext> {

}
