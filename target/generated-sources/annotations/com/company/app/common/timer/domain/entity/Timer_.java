package com.company.app.common.timer.domain.entity;

import com.company.app.common.timer.domain.enums.ActionType;
import com.company.app.common.timer.domain.enums.StatusType;
import java.time.ZonedDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Timer.class)
public abstract class Timer_ {

	public static volatile SingularAttribute<Timer, ActionType> actionType;
	public static volatile SingularAttribute<Timer, ZonedDateTime> updateDate;
	public static volatile SingularAttribute<Timer, String> entityView;
	public static volatile SingularAttribute<Timer, StatusType> statusType;
	public static volatile SingularAttribute<Timer, Long> id;
	public static volatile SingularAttribute<Timer, ZonedDateTime> createDate;

	public static final String ACTION_TYPE = "actionType";
	public static final String UPDATE_DATE = "updateDate";
	public static final String ENTITY_VIEW = "entityView";
	public static final String STATUS_TYPE = "statusType";
	public static final String ID = "id";
	public static final String CREATE_DATE = "createDate";

}

