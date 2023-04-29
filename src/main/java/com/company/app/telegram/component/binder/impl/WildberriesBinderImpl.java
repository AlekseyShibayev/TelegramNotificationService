package com.company.app.telegram.component.binder.impl;

import com.company.app.telegram.component.binder.BinderContainer;
import com.company.app.telegram.component.binder.api.WildberriesBinder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class WildberriesBinderImpl implements WildberriesBinder {

	private static final String TYPE = "WB";

	@Override
	public String getType() {
		return TYPE;
	}

	@SneakyThrows
	@Override
	public void bind(BinderContainer binderContainer) {

		String ыекштп = "WB [{\"article\":\"17010096\",\"desiredPrice\":\"900\",\"discount\":\"0.11\"}]";

		// todo
	}
}