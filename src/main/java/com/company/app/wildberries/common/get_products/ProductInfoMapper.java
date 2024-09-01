package com.company.app.wildberries.common.get_products;

import com.company.app.core.util.Strings;
import com.company.app.wildberries.common.model.VmProduct;
import com.company.app.wildberries.common.model.v2.VmPrice;
import org.mapstruct.BeanMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.math.BigDecimal;

@Mapper(componentModel = "spring"
        , unmappedTargetPolicy = ReportingPolicy.WARN
        , injectionStrategy = InjectionStrategy.CONSTRUCTOR
        , imports = {}
        , uses = {}
)
public interface ProductInfoMapper {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "article", source = "vmProduct.id")
    @Mapping(target = "description",  source = "vmProduct.name")
    @Mapping(target = "price", expression = "java(getPrice(vmProduct))")
    @Mapping(target = "rating",  source = "vmProduct.rating")
    @Mapping(target = "feedbacks", source = "vmProduct.feedbacks")
    ProductInfo map(VmProduct vmProduct);

    default BigDecimal getPrice(VmProduct vmProduct) {
        VmPrice price = vmProduct.getSizes().get(0).getPrice();
        return price == null ? BigDecimal.ZERO : new BigDecimal(Strings.cutEnd(price.getProduct(), 2));
    }

}
