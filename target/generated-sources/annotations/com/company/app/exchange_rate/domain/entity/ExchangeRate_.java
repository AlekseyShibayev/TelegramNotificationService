package com.company.app.exchange_rate.domain.entity;

import com.company.app.exchange_rate.domain.enums.ExchangeRateType;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ExchangeRate.class)
public abstract class ExchangeRate_ {

	public static volatile SingularAttribute<ExchangeRate, Long> id;
	public static volatile SingularAttribute<ExchangeRate, ExchangeRateType> type;
	public static volatile SingularAttribute<ExchangeRate, Date> creationDate;
	public static volatile SingularAttribute<ExchangeRate, String> value;

	public static final String ID = "id";
	public static final String TYPE = "type";
	public static final String CREATION_DATE = "creationDate";
	public static final String VALUE = "value";

}

