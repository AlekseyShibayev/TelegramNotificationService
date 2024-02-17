package com.company.app.telegram.domain.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Mode.class)
public abstract class Mode_ {

	public static volatile ListAttribute<Mode, Chat> chats;
	public static volatile SingularAttribute<Mode, Long> id;
	public static volatile SingularAttribute<Mode, String> type;

	public static final String CHATS = "chats";
	public static final String ID = "id";
	public static final String TYPE = "type";

}

