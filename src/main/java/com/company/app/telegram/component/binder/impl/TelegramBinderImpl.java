package com.company.app.telegram.component.binder.impl;

import com.company.app.telegram.component.TelegramFacade;
import com.company.app.telegram.component.binder.BinderContainer;
import com.company.app.telegram.component.binder.api.TelegramBinder;
import com.company.app.telegram.entity.Chat;
import com.company.app.telegram.service.api.ChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TelegramBinderImpl implements TelegramBinder {

	private static final String TYPE = "TG";

	@Autowired
	ChatService chatService;
	@Autowired
	TelegramFacade telegramFacade;

	@Override
	public String getType() {
		return TYPE;
	}

	@Override
	public void bind(BinderContainer binderContainer) {
		Chat chat = binderContainer.getChat();
		chat.setEnableNotifications(false);

		chatService.update(chat);

		notify(chat);
	}

	private void notify(Chat chat) {
		String message = "Отключение уведомлений выполнено.";
		if (log.isDebugEnabled()) {
			log.debug("[{}]: Отключение уведомлений выполнено.", chat.getChatId());
		}
		telegramFacade.writeToTargetChat(chat.getChatId(), message);
	}

//	@Override
//	public void bind(BinderContainer binderContainer) {
//		Chat chat = binderContainer.getChat();
//		String message = binderContainer.getMessage().substring(3);
//
//		if (message.equals("+")) {
//			chat.setEnableNotifications(true);
//			Set<Subscription> subscriptions = subscriptionController.readAll().getBody();
//			chat.setSubscriptions(subscriptions);
//		} else if (message.equals("-")) {
//			chat.setEnableNotifications(false);
//		}
//
//		ChatDto chatDto = ChatUtil.of(chat);
//		chatController.update(chat.getId(), chatDto);
//	}
}
