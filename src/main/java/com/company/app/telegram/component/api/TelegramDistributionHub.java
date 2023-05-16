package com.company.app.telegram.component.api;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface TelegramDistributionHub {

	void read(Update update);

	void writeToEveryone(Object message);

	void writeToTargetChat(String chatName, Object message);
}
