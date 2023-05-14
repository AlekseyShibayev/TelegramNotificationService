package com.company.app.telegram.binder.impl;

import com.company.app.telegram.binder.BinderContainer;
import com.company.app.telegram.binder.api.WildberriesBinder;
import com.company.app.telegram.component.TelegramFacade;
import com.company.app.telegram.domain.entity.Chat;
import com.company.app.telegram.domain.entity.UserInfo;
import com.company.app.wildberries_desire_lot.controller.WildberriesController;
import com.company.app.wildberries_desire_lot.domain.dto.WildberriesLinkDto;
import com.company.app.wildberries_searcher.data.WildberriesSearcherContainer;
import com.company.app.wildberries_searcher.data.WildberriesSearcherResult;
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

		WildberriesSearcherResult result = wildberriesController.search(wildberriesSearcherContainer).getBody();

		if (result.isNotSuccess()) {
			telegramFacade.writeToTargetChat(chat.getChatId(), "Что-то не так");
		} else if (result.isSuccess() && result.getWildberriesLinkDtoList().isEmpty()) {
			telegramFacade.writeToTargetChat(chat.getChatId(), "Пусто");
		} else {
			List<WildberriesLinkDto> dtoList = result.getWildberriesLinkDtoList();
			List<String> list = dtoList.stream()
					.map(dto -> dto.getPrice() + ": " + dto.getLink())
					.distinct()
					.collect(Collectors.toList());
			list.forEach(message -> telegramFacade.writeToTargetChat(chat.getChatId(), message));
		}
	}
}
