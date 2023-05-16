package com.company.app.telegram.binder.impl;

import com.company.app.telegram.binder.BinderContainer;
import com.company.app.telegram.binder.api.WildberriesBinder;
import com.company.app.telegram.component.TelegramFacade;
import com.company.app.telegram.domain.entity.Chat;
import com.company.app.telegram.domain.entity.UserInfo;
import com.company.app.wildberries.component.searcher.data.WildberriesSearcherContainer;
import com.company.app.wildberries.component.searcher.data.WildberriesSearcherResult;
import com.company.app.wildberries.controller.WildberriesController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
				.chatName(chat.getChatName())
				.dressSize(userInfo.getDressSize())
				.footSize(userInfo.getFootSize())
				.gender(userInfo.getGender())
				.supplier(userInfo.getSupplier())
				.greedIndex(userInfo.getGreedIndex())
				.build();

		WildberriesSearcherResult result = wildberriesController.search(wildberriesSearcherContainer).getBody();

		if (result.isNotSuccess()) {
			String message = "Занято! Вы что 5 лет в разработке и ни разу не использовали семафор???";
			telegramFacade.writeToTargetChat(chat.getChatName(), message);
		} else if (result.isSuccess()) {
			String message = "Поисковая задача успешно запущена.";
			telegramFacade.writeToTargetChat(chat.getChatName(), message);
		}
	}
}
