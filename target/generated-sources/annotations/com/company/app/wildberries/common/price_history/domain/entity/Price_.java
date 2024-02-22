package com.company.app.wildberries.common.price_history.domain.entity;

import java.time.ZonedDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Price.class)
public abstract class Price_ {

	public static volatile SingularAttribute<Price, Product> product;
	public static volatile SingularAttribute<Price, ZonedDateTime> updateDate;
	public static volatile SingularAttribute<Price, String> cost;
	public static volatile SingularAttribute<Price, Long> id;
	public static volatile SingularAttribute<Price, ZonedDateTime> createDate;

	public static final String PRODUCT = "product";
	public static final String UPDATE_DATE = "updateDate";
	public static final String COST = "cost";
	public static final String ID = "id";
	public static final String CREATE_DATE = "createDate";

}

