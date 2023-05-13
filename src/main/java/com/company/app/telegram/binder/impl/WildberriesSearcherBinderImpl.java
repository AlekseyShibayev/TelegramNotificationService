package com.company.app.telegram.binder.impl;

import com.company.app.telegram.binder.BinderContainer;
import com.company.app.telegram.binder.api.WildberriesBinder;
import com.company.app.telegram.component.TelegramFacade;
import com.company.app.telegram.domain.entity.Chat;
import com.company.app.telegram.domain.entity.UserInfo;
import com.company.app.wildberries.component.searcher.data.WildberriesSearcherContainer;
import com.company.app.wildberries.controller.WildberriesController;
import com.company.app.wildberries.domain.dto.WildberriesLinkDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class WildberriesSearcherBinderImpl implements WildberriesBinder {

	private static final String TYPE = "WB_SEARCH";

	@Autowired
	private WildberriesController wildberriesController;
	@Autowired
	private TelegramFacade telegramFacade;

	@Override
	public String getType() {
		return TYPE;
	}

	@Override
	public void bind(BinderContainer binderContainer) {
		Chat chat = binderContainer.getChat();
		UserInfo userInfo = chat.getUserInfo();

		WildberriesSearcherContainer wildberriesSearcherContainer = WildberriesSearcherContainer.builder()
				.chatId(chat.getChatId().toString())
				.dressSize(userInfo.getDressSize())
				.footSize(userInfo.getFootSize())
				.gender(userInfo.getGender())
				.build();

		List<WildberriesLinkDto> dtos = wildberriesController.search(wildberriesSearcherContainer).getBody();

		if (CollectionUtils.isEmpty(dtos)) {
			telegramFacade.writeToTargetChat(chat.getChatId(), "Пусто");
		} else {
			List<String> list = dtos.stream()
					.map(dto -> dto.getPrice() + ": " + dto.getLink())
					.distinct()
					.collect(Collectors.toList());
			list.forEach(message -> telegramFacade.writeToTargetChat(chat.getChatId(), message));
		}
	}
}
