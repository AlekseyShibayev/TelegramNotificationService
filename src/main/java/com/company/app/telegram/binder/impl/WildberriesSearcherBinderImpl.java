package com.company.app.telegram.binder.impl;

import com.company.app.telegram.binder.BinderContainer;
import com.company.app.telegram.binder.api.WildberriesBinder;
import com.company.app.telegram.component.TelegramFacade;
import com.company.app.telegram.domain.entity.Chat;
import com.company.app.wildberries_searcher.component.data.WildberriesSearcherContainer;
import com.company.app.wildberries_searcher.component.data.WildberriesSearcherResult;
import com.company.app.wildberries_searcher.controller.WildberriesSearcherController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WildberriesSearcherBinderImpl implements WildberriesBinder {

	private static final String TYPE = "WB_SEARCH";

	@Autowired
	private WildberriesSearcherController wildberriesSearcherController;
	@Autowired
	private TelegramFacade telegramFacade;

	@Override
	public String getType() {
		return TYPE;
	}

	@Override
	public void bind(BinderContainer binderContainer) {
		Chat chat = binderContainer.getChat();

		WildberriesSearcherContainer wildberriesSearcherContainer = WildberriesSearcherContainer.builder()
				.chatName(chat.getChatName())
				.build();

		WildberriesSearcherResult result = wildberriesSearcherController.search(wildberriesSearcherContainer).getBody();

		if (result.isNotSuccess()) {
			String message = "Занято! Вы что 5 лет в разработке и ни разу не использовали семафор???";
			telegramFacade.writeToTargetChat(chat.getChatName(), message);
		} else if (result.isSuccess()) {
			String message = "Поисковая задача успешно запущена.";
			telegramFacade.writeToTargetChat(chat.getChatName(), message);
		}
	}
}
