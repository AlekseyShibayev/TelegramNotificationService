package com.company.app.telegram.domain.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(UserInfo.class)
public abstract class UserInfo_ {

	public static volatile SingularAttribute<UserInfo, String> role;
	public static volatile SingularAttribute<UserInfo, String> gender;
	public static volatile SingularAttribute<UserInfo, Chat> chat;
	public static volatile SingularAttribute<UserInfo, String> name;
	public static volatile SingularAttribute<UserInfo, Long> id;

	public static final String ROLE = "role";
	public static final String GENDER = "gender";
	public static final String CHAT = "chat";
	public static final String NAME = "name";
	public static final String ID = "id";

}

