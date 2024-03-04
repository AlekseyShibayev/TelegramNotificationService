package com.company.app.habr.domain.entity;

import java.time.ZonedDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(HabrUser.class)
public abstract class HabrUser_ {

	public static volatile SingularAttribute<HabrUser, ZonedDateTime> updateDate;
	public static volatile SingularAttribute<HabrUser, Habr> habr;
	public static volatile SingularAttribute<HabrUser, Long> id;
	public static volatile SingularAttribute<HabrUser, ZonedDateTime> createDate;

	public static final String UPDATE_DATE = "updateDate";
	public static final String HABR = "habr";
	public static final String ID = "id";
	public static final String CREATE_DATE = "createDate";

}

