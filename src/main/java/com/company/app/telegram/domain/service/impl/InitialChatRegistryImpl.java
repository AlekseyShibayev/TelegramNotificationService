package com.company.app.telegram.domain.service.impl;

import com.company.app.core.tool.api.JsonSerializationTool;
import com.company.app.telegram.domain.entity.Chat;
import com.company.app.telegram.domain.service.api.ChatService;
import com.company.app.telegram.domain.service.api.InitialChatRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class InitialChatRegistryImpl implements InitialChatRegistry {

	@Value("classpath:telegram/init_chat.json")
	private Resource resource;

	@Autowired
	private JsonSerializationTool<Chat> jsonSerializationTool;
	@Autowired
	private ChatService chatService;

	@PostConstruct
	public void init() throws TelegramApiException {
		List<Chat> list = jsonSerializationTool.load(resource, Chat.class);
		chatService.saveAll(list);
	}
}
