package com.company.app.common.outbox.domain.entity;

import com.company.app.common.outbox.domain.enums.Status;
import com.company.app.common.outbox.domain.enums.Target;
import java.time.ZonedDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Outbox.class)
public abstract class Outbox_ {

	public static volatile SingularAttribute<Outbox, ZonedDateTime> updateDate;
	public static volatile SingularAttribute<Outbox, String> what;
	public static volatile SingularAttribute<Outbox, Long> id;
	public static volatile SingularAttribute<Outbox, String> who;
	public static volatile SingularAttribute<Outbox, Target> target;
	public static volatile SingularAttribute<Outbox, Status> status;
	public static volatile SingularAttribute<Outbox, ZonedDateTime> createDate;

	public static final String UPDATE_DATE = "updateDate";
	public static final String WHAT = "what";
	public static final String ID = "id";
	public static final String WHO = "who";
	public static final String TARGET = "target";
	public static final String STATUS = "status";
	public static final String CREATE_DATE = "createDate";

}

