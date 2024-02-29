package com.company.app.wildberries.common.price_history.domain.entity;

import java.time.ZonedDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Product.class)
public abstract class Product_ {

	public static volatile SingularAttribute<Product, ZonedDateTime> updateDate;
	public static volatile SingularAttribute<Product, String> historyPriceUrl;
	public static volatile ListAttribute<Product, Price> price;
	public static volatile SingularAttribute<Product, Long> id;
	public static volatile SingularAttribute<Product, String> article;
	public static volatile SingularAttribute<Product, ZonedDateTime> createDate;

	public static final String UPDATE_DATE = "updateDate";
	public static final String HISTORY_PRICE_URL = "historyPriceUrl";
	public static final String PRICE = "price";
	public static final String ID = "id";
	public static final String ARTICLE = "article";
	public static final String CREATE_DATE = "createDate";

}

