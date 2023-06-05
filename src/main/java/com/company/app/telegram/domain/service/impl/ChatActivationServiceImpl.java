package com.company.app.telegram.domain.service.impl;

import com.company.app.telegram.component.TelegramFacade;
import com.company.app.telegram.controller.SubscriptionController;
import com.company.app.telegram.domain.entity.Chat;
import com.company.app.telegram.domain.entity.Subscription;
import com.company.app.telegram.domain.service.api.ChatActivationService;
import com.company.app.telegram.domain.service.api.ChatService;
import lombok.extern.slf4j.Slf4j;
import org.glassfish.jersey.internal.guava.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Slf4j
@Component
public class ChatActivationServiceImpl implements ChatActivationService {

    @Autowired
    private SubscriptionController subscriptionController;
    @Autowired
    private ChatService chatService;
    @Autowired
    private TelegramFacade telegramFacade;

    @Transactional
    @Override
    public void activate(Chat chat) {
        if (isChatNotActive(chat)) {
            chat.setEnableNotifications(true);
            Set<Subscription> subscriptions = subscriptionController.readAll().getBody();
            chat.setSubscriptions(subscriptions);

            chatService.update(chat);

            String message = String.format("Для чата [%s] уведомления включены.", chat.getChatName());
            telegramFacade.writeToTargetChat(chat.getChatName(), message);
        }
    }

    @Transactional
    @Override
    public void deactivate(Chat chat) {
        if (!isChatNotActive(chat)) {
            chat.setEnableNotifications(false);
            chat.setSubscriptions(Sets.newHashSet());

            chatService.update(chat);

            String message = String.format("Для чата [%s] уведомления отключены.", chat.getChatName());
            telegramFacade.writeToTargetChat(chat.getChatName(), message);
        }
    }

    private boolean isChatNotActive(Chat chat) {
        return !chat.isEnableNotifications();
    }
}
