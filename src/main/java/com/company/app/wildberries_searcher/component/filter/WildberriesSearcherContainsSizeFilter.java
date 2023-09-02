package com.company.app.wildberries_searcher.component.filter;

import com.company.app.core.data.ResponseProducts;
import com.company.app.core.data.Size;
import com.company.app.core.util.Strings;
import com.company.app.wildberries_searcher.component.data.WildberriesSearcherContext;
import com.google.common.collect.Sets;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public class WildberriesSearcherContainsSizeFilter implements WildberriesSearcherFilter {

    private static final String TYPE = "containsSize";

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public boolean isPreFilter() {
        return true;
    }

    @Override
    public boolean doFilter(ResponseProducts responseProducts, WildberriesSearcherContext wildberriesSearcherContainer) {
        return withContainsSize(responseProducts, wildberriesSearcherContainer);
    }

    private boolean withContainsSize(ResponseProducts responseProducts, WildberriesSearcherContext wildberriesSearcherContainer) {
        Optional<Size> optional = getUserSize(responseProducts, wildberriesSearcherContainer.getDressSize());
        return optional.isPresent();
    }

    Optional<Size> getUserSize(ResponseProducts responseProducts, String userSizes) {
        List<Size> productSizes = responseProducts.getSizes();
        Set<String> userSizesSet = Sets.newHashSet(userSizes.split(Strings.DEFAULT_DELIMITER));
        return productSizes.stream()
                .filter(size -> userSizesSet.contains(size.getName()))
                .findAny();
    }
}
