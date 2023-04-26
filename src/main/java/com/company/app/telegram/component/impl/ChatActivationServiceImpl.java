package com.company.app.telegram.component.impl;

import com.company.app.telegram.component.TelegramFacade;
import com.company.app.telegram.component.api.ChatActivationService;
import com.company.app.telegram.controller.SubscriptionController;
import com.company.app.telegram.entity.Chat;
import com.company.app.telegram.entity.Subscription;
import com.company.app.telegram.service.api.ChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

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
	public void doFullActivate(Chat chat) {
		if (isChatNotActive(chat)) {
			chat.setEnableNotifications(true);

			Set<Subscription> subscriptions = subscriptionController.readAll().getBody();
			chat.setSubscriptions(subscriptions);

			chatService.update(chat);
			notify(chat, subscriptions);
		}
	}

	private boolean isChatNotActive(Chat chat) {
		return !chat.isEnableNotifications();
	}

	private void notify(Chat chat, Set<Subscription> subscriptions) {
		String message = String.format("Полная активация выполнена. Чат [%s] подписан на следующие рассылки: [%s].",
				chat.getChatId(), subscriptions.stream().map(Subscription::getType).collect(Collectors.toList()));
		telegramFacade.writeToTargetChat(chat.getChatId(), message);
	}
}
