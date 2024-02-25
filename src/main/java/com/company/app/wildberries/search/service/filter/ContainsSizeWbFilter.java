package com.company.app.wildberries.search.service.filter;

import com.company.app.core.util.Strings;
import com.company.app.wildberries.common.model.VmProduct;
import com.company.app.wildberries.common.model.VmSize;
import com.company.app.wildberries.search.model.WbSearchContext;
import com.google.common.collect.Sets;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
public class ContainsSizeWbFilter implements WbFilter {

    @Override
    public boolean test(VmProduct product, WbSearchContext context) {
        return getUserSize(product, context.getDressSize()).isPresent();
    }

    Optional<VmSize> getUserSize(VmProduct product, String userSizes) {
        List<VmSize> productSizes = product.getSizes();
        Set<String> userSizesSet = Sets.newHashSet(userSizes.split(Strings.DEFAULT_DELIMITER));
        return productSizes.stream()
                .filter(size -> userSizesSet.contains(size.getName()))
                .findAny();
    }

}