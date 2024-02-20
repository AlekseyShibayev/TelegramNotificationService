package com.company.app.wildberries.desire.domain.entity;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(DesireLot.class)
public abstract class DesireLot_ {

	public static volatile SingularAttribute<DesireLot, BigDecimal> price;
	public static volatile SingularAttribute<DesireLot, String> description;
	public static volatile ListAttribute<DesireLot, Desire> desireList;
	public static volatile SingularAttribute<DesireLot, OffsetDateTime> updateTime;
	public static volatile SingularAttribute<DesireLot, Long> id;
	public static volatile SingularAttribute<DesireLot, String> article;

	public static final String PRICE = "price";
	public static final String DESCRIPTION = "description";
	public static final String DESIRE_LIST = "desireList";
	public static final String UPDATE_TIME = "updateTime";
	public static final String ID = "id";
	public static final String ARTICLE = "article";

}

