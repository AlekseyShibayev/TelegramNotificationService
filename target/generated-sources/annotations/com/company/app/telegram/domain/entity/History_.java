package com.company.app.telegram.domain.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(History.class)
public abstract class History_ {

	public static volatile SingularAttribute<History, Date> date;
	public static volatile SingularAttribute<History, Chat> chat;
	public static volatile SingularAttribute<History, Long> id;
	public static volatile SingularAttribute<History, String> source;
	public static volatile SingularAttribute<History, String> message;
	public static volatile SingularAttribute<History, String> target;

	public static final String DATE = "date";
	public static final String CHAT = "chat";
	public static final String ID = "id";
	public static final String SOURCE = "source";
	public static final String MESSAGE = "message";
	public static final String TARGET = "target";

}

