package com.company.app.telegram.domain.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Subscription.class)
public abstract class Subscription_ {

	public static volatile ListAttribute<Subscription, Chat> chats;
	public static volatile SingularAttribute<Subscription, Long> id;
	public static volatile SingularAttribute<Subscription, String> type;

	public static final String CHATS = "chats";
	public static final String ID = "id";
	public static final String TYPE = "type";

}

