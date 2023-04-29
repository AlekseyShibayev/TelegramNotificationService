package com.company.app.telegram.component.impl;

import com.company.app.telegram.component.TelegramFacade;
import com.company.app.telegram.component.api.ChatActivationService;
import com.company.app.telegram.controller.SubscriptionController;
import com.company.app.telegram.entity.Chat;
import com.company.app.telegram.entity.Subscription;
import com.company.app.telegram.service.api.ChatService;
import lombok.extern.slf4j.Slf4j;
import org.glassfish.jersey.internal.guava.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Slf4j
@Component
public class ChatActivationServiceImpl implements ChatActivationService {

	@Autowired
	SubscriptionController subscriptionController;
	@Autowired
	ChatService chatService;
	@Autowired
	TelegramFacade telegramFacade;

	@Override
	public void activate(Chat chat) {
		if (isChatNotActive(chat)) {
			chat.setEnableNotifications(true);
			Set<Subscription> subscriptions = subscriptionController.readAll().getBody();
			chat.setSubscriptions(subscriptions);

			chatService.update(chat);

			String message = String.format("Для чата [%s] уведомления включены.", chat.getChatId());
			telegramFacade.writeToTargetChat(chat.getChatId(), message);
		}
	}

	@Override
	public void deactivate(Chat chat) {
		if (!isChatNotActive(chat)) {
			chat.setEnableNotifications(false);
			chat.setSubscriptions(Sets.newHashSet());

			chatService.update(chat);

			String message = String.format("Для чата [%s] уведомления отключены.", chat.getChatId());
			telegramFacade.writeToTargetChat(chat.getChatId(), message);
		}
	}

	private boolean isChatNotActive(Chat chat) {
		return !chat.isEnableNotifications();
	}
}
