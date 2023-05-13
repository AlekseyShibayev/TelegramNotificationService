package com.company.app.telegram.binder.impl;

import com.company.app.telegram.binder.BinderContainer;
import com.company.app.telegram.binder.api.WildberriesBinder;
import com.company.app.telegram.component.TelegramFacade;
import com.company.app.telegram.domain.entity.Chat;
import com.company.app.wildberries.controller.WildberriesController;
import com.company.app.wildberries.domain.dto.FoundItemDto;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Slf4j
@Component
public class WildberriesBinderImpl implements WildberriesBinder {

	private static final String TYPE = "WB";

	@Autowired
	private WildberriesController wildberriesController;
	@Autowired
	private TelegramFacade telegramFacade;

	@Override
	public String getType() {
		return TYPE;
	}

	@SneakyThrows
	@Override
	public void bind(BinderContainer binderContainer) {
		Chat chat = binderContainer.getChat();
		List<FoundItemDto> foundItemDtoList = wildberriesController.getAllFoundItems().getBody();
		if (CollectionUtils.isEmpty(foundItemDtoList)) {
			telegramFacade.writeToTargetChat(chat.getChatId(), "Пусто");
		} else {
			String message = foundItemDtoList.stream()
					.map(FoundItemDto::getLink)
					.distinct()
					.reduce((s, s2) -> s + "\n" + s2)
					.orElseThrow();
			telegramFacade.writeToTargetChat(chat.getChatId(), message);
		}
	}
}