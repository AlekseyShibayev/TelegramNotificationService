package com.company.app.telegram.component.impl;

import com.company.app.telegram.component.api.Binder;
import com.company.app.telegram.component.api.BinderExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class BinderExecutorImpl implements BinderExecutor {

	@Autowired
	List<Binder> binderList;
	Map<String, Binder> binders;

	@PostConstruct
	void init() {
		binders = binderList.stream().collect(Collectors.toMap(Binder::getType, Function.identity()));
	}

	@Override
	public void execute(String text) {
		String binderType = Arrays.stream(text.split(" ")).findFirst().orElseThrow();
		Binder binder = Optional.ofNullable(binders.get(binderType))
				.orElseThrow(() -> new IllegalArgumentException(String.format("Не смог вытащить тип binderType из [%s].", text)));
		binder.bind(text);
	}
}
