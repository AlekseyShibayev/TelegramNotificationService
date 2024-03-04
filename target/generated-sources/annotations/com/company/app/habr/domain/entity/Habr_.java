package com.company.app.habr.domain.entity;

import com.company.app.habr.domain.enums.Status;
import java.time.ZonedDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Habr.class)
public abstract class Habr_ {

	public static volatile ListAttribute<Habr, HabrUser> habrUsers;
	public static volatile SingularAttribute<Habr, ZonedDateTime> updateDate;
	public static volatile SingularAttribute<Habr, Long> id;
	public static volatile SingularAttribute<Habr, Status> status;
	public static volatile SingularAttribute<Habr, ZonedDateTime> createDate;

	public static final String HABR_USERS = "habrUsers";
	public static final String UPDATE_DATE = "updateDate";
	public static final String ID = "id";
	public static final String STATUS = "status";
	public static final String CREATE_DATE = "createDate";

}

