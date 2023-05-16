package com.company.app.telegram.component;

import com.company.app.telegram.component.api.TelegramDistributionHub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TelegramFacade {

	@Autowired
	private TelegramDistributionHub telegramDistributionHub;

	public void writeToEveryone(Object message) {
		telegramDistributionHub.writeToEveryone(message);
	}

	public void writeToTargetChat(String chatName, Object message) {
		telegramDistributionHub.writeToTargetChat(chatName, message);
	}
}
