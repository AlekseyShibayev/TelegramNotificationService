package com.company.app.telegram.component.binder.api;

import com.company.app.telegram.component.binder.BinderContainer;

/**
 * Связывает модуль и входящее сообщение из телеграмм.
 */
public interface Binder {

	String getType();

	void bind(BinderContainer binderContainer);
}
