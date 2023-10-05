package com.company.app.telegram.domain.initializer;

import com.company.app.core.temp.tool.json.JsonTool;
import com.company.app.telegram.domain.entity.Chat;
import com.company.app.telegram.domain.entity.Mode;
import com.company.app.telegram.domain.entity.Subscription;
import com.company.app.telegram.domain.enums.ModeType;
import com.company.app.telegram.domain.repository.ModeRepository;
import com.company.app.telegram.domain.repository.SubscriptionRepository;
import com.company.app.telegram.domain.service.ChatService;
import com.company.app.telegram.incoming_message.binder.binder_strategy.Binder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * Initializes different @Entity, when application started.
 */
@Service
@RequiredArgsConstructor
public class Initializer {

    @Value("classpath:telegram/init_chat.json")
    private Resource resource;

    private final JsonTool<Chat> jsonTool;
    private final ChatService chatService;
    private final ModeRepository modeRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final List<Binder> binderList;

    @EventListener({ContextRefreshedEvent.class})
    @Transactional
    public void init() {
        prepareMode();
        prepareChat();
        prepareSubscriptions();
    }

    private void prepareChat() {
        List<Chat> list = jsonTool.toJavaAsList(resource, Chat.class);
        Mode defaultMode = modeRepository.findByType(ModeType.DEFAULT);

        list.forEach(chat -> chat.setMode(defaultMode));
        chatService.saveAll(list);
    }

    private void prepareMode() {
        List<Mode> list = Arrays.stream(ModeType.values())
                .map(chatModeType -> new Mode()
                        .setType(chatModeType.getType()))
                .toList();

        modeRepository.saveAll(list);
    }

    void prepareSubscriptions() {
        List<Subscription> subscriptionList = binderList.stream()
                .map(binderType -> Subscription.builder().type(binderType.getType()).build())
                .toList();
        subscriptionRepository.saveAll(subscriptionList);
    }

}
