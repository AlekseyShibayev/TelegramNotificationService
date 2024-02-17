package com.company.app.telegram.domain.entity;

import java.time.OffsetDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(IncomingMessageTask.class)
public abstract class IncomingMessageTask_ {

	public static volatile SingularAttribute<IncomingMessageTask, String> modeType;
	public static volatile SingularAttribute<IncomingMessageTask, OffsetDateTime> createTime;
	public static volatile SingularAttribute<IncomingMessageTask, String> chatName;
	public static volatile SingularAttribute<IncomingMessageTask, Long> id;
	public static volatile SingularAttribute<IncomingMessageTask, String> message;

	public static final String MODE_TYPE = "modeType";
	public static final String CREATE_TIME = "createTime";
	public static final String CHAT_NAME = "chatName";
	public static final String ID = "id";
	public static final String MESSAGE = "message";

}

