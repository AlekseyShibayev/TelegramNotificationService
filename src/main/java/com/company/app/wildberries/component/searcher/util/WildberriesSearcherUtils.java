package com.company.app.wildberries.component.searcher.util;

import com.company.app.telegram.domain.dto.TargetMessage;
import com.company.app.wildberries.domain.dto.WildberriesLinkDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class WildberriesSearcherUtils {

	public String createMessage(WildberriesLinkDto dto) {
		return dto.getPrice() + ": " + dto.getLink();
	}

	public TargetMessage createTargetMessage(String chatId, String message) {
		return TargetMessage.builder()
				.chatName(chatId)
				.message(message)
				.build();
	}
}