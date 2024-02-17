package com.company.app.telegram.domain.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Chat.class)
public abstract class Chat_ {

	public static volatile SingularAttribute<Chat, Mode> mode;
	public static volatile SingularAttribute<Chat, UserInfo> userInfo;
	public static volatile ListAttribute<Chat, Subscription> subscriptions;
	public static volatile SingularAttribute<Chat, Boolean> enableNotifications;
	public static volatile SingularAttribute<Chat, String> chatName;
	public static volatile ListAttribute<Chat, History> histories;
	public static volatile SingularAttribute<Chat, Long> id;

	public static final String MODE = "mode";
	public static final String USER_INFO = "userInfo";
	public static final String SUBSCRIPTIONS = "subscriptions";
	public static final String ENABLE_NOTIFICATIONS = "enableNotifications";
	public static final String CHAT_NAME = "chatName";
	public static final String HISTORIES = "histories";
	public static final String ID = "id";

}

