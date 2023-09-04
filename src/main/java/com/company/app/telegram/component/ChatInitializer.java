package com.company.app.telegram.component;

import com.company.app.core.temp.tool.json.JsonTool;
import com.company.app.telegram.domain.entity.Chat;
import com.company.app.telegram.domain.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

/**
 * Регистрирует чаты, при старте приложения.
 */
@Service
@RequiredArgsConstructor
public class ChatInitializer {

    @Value("classpath:telegram/init_chat.json")
    private Resource resource;

    private final JsonTool<Chat> jsonTool;
    private final ChatService chatService;

    @EventListener({ContextRefreshedEvent.class})
    public void init() throws TelegramApiException {
        List<Chat> list = jsonTool.toJavaAsList(resource, Chat.class);
        chatService.saveAll(list);
    }

}
