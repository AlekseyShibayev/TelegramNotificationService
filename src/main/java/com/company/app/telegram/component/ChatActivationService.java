package com.company.app.telegram.component;

import com.company.app.telegram.TelegramFacade;
import com.company.app.telegram.domain.entity.Chat;
import com.company.app.telegram.domain.entity.Subscription;
import com.company.app.telegram.domain.repository.SubscriptionRepository;
import com.company.app.telegram.domain.service.ChatService;
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
    private final ChatService chatService;
    private final TelegramFacade telegramFacade;

    public void activate(Chat chat) {
        if (isChatNotActive(chat)) {
            chat.setEnableNotifications(true);
            List<Subscription> subscriptions = subscriptionRepository.findAll();
            chat.setSubscriptions(subscriptions);

            chatService.update(chat);

            String message = String.format("Для чата [%s] уведомления включены.", chat.getChatName());
            telegramFacade.writeToTargetChat(chat.getChatName(), message);
        }
    }

    public void deactivate(Chat chat) {
        if (!isChatNotActive(chat)) {
            chat.setEnableNotifications(false);
            chat.setSubscriptions(new ArrayList<>());

            chatService.update(chat);

            String message = String.format("Для чата [%s] уведомления отключены.", chat.getChatName());
            telegramFacade.writeToTargetChat(chat.getChatName(), message);
        }
    }

    private boolean isChatNotActive(Chat chat) {
        return !chat.isEnableNotifications();
    }

}
