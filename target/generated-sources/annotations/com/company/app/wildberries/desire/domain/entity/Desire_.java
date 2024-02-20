package com.company.app.wildberries.desire.domain.entity;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Desire.class)
public abstract class Desire_ {

	public static volatile SingularAttribute<Desire, BigDecimal> price;
	public static volatile SingularAttribute<Desire, DesireLot> desireLot;
	public static volatile SingularAttribute<Desire, String> chatName;
	public static volatile SingularAttribute<Desire, Long> id;
	public static volatile SingularAttribute<Desire, String> article;

	public static final String PRICE = "price";
	public static final String DESIRE_LOT = "desireLot";
	public static final String CHAT_NAME = "chatName";
	public static final String ID = "id";
	public static final String ARTICLE = "article";

}

