package com.company.app.telegram.incoming_message_handler.service;

import com.company.app.telegram.TelegramFacade;
import com.company.app.telegram.domain.entity.Chat;
import com.company.app.telegram.domain.entity.Subscription;
import com.company.app.telegram.domain.repository.ChatRepository;
import com.company.app.telegram.domain.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Отвечает за активацию чата.
 */
@Service
@RequiredArgsConstructor
public class ChatActivationService {

    private final SubscriptionRepository subscriptionRepository;
    private final ChatRepository chatRepository;
    private final TelegramFacade telegramFacade;

    public Chat activate(Chat chat) {
        if (!chat.isEnableNotifications()) {
            chat.setEnableNotifications(true);
            List<Subscription> subscriptions = subscriptionRepository.findAll();
            chat.setSubscriptions(subscriptions);

            Chat saved = chatRepository.save(chat);

            String message = String.format("Для чата [%s] уведомления включены.", chat.getChatName());
            telegramFacade.writeToTargetChat(chat.getChatName(), message);

            return saved;
        } else {
            return chat;
        }
    }

    public Chat deactivate(Chat chat) {
        if (chat.isEnableNotifications()) {
            chat.setEnableNotifications(false);
            chat.setSubscriptions(new ArrayList<>());

            Chat saved = chatRepository.save(chat);

            String message = String.format("Для чата [%s] уведомления отключены.", chat.getChatName());
            telegramFacade.writeToTargetChat(chat.getChatName(), message);

            return saved;
        } else {
            return chat;
        }
    }

}