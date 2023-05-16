package com.company.app.wildberries.component.searcher.impl;

import com.company.app.telegram.controller.TelegramController;
import com.company.app.telegram.domain.dto.TargetMessage;
import com.company.app.wildberries.component.desire_lot.data.ResponseProducts;
import com.company.app.wildberries.component.searcher.api.WildberriesSearcherNotificator;
import com.company.app.wildberries.component.searcher.data.WildberriesSearcherContainer;
import com.company.app.wildberries.component.searcher.util.WildberriesSearcherUtils;
import com.company.app.wildberries.domain.dto.WildberriesLinkDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WildberriesSearcherNotificatorImpl implements WildberriesSearcherNotificator {

	@Autowired
	private TelegramController telegramController;

	@Override
	public void notify(ResponseProducts responseProducts, WildberriesSearcherContainer wildberriesSearcherContainer) {
		WildberriesLinkDto dto = responseProducts.to();
		String message = WildberriesSearcherUtils.createMessage(dto);
		TargetMessage targetMessage = WildberriesSearcherUtils.createTargetMessage(wildberriesSearcherContainer.getChatName(), message);
		telegramController.say(targetMessage);
	}
}
