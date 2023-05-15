package com.company.app.wildberries_searcher.data;

import com.company.app.telegram.controller.TelegramController;
import com.company.app.telegram.domain.dto.TargetMessage;
import com.company.app.wildberries_desire_lot.domain.dto.WildberriesLinkDto;
import com.company.app.wildberries_searcher.api.WildberriesSearcher;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.concurrent.ExecutorService;

@Data
@Builder
public class WildberriesSearcherTask implements Runnable {

	private WildberriesSearcherContainer wildberriesSearcherContainer;
	private WildberriesSearcher wildberriesSearcher;
	private TelegramController telegramController;
	private ExecutorService executorService;

	@Override
	public void run() {
		List<WildberriesLinkDto> dtoList = wildberriesSearcher.search(this.wildberriesSearcherContainer);
		dtoList.stream()
				.map(this::createMessage)
				.distinct()
				.map(this::createTargetMessage)
				.forEach(targetMessage -> telegramController.say(targetMessage));
		executorService.shutdown();
	}

	private String createMessage(WildberriesLinkDto dto) {
		return dto.getPrice() + ": " + dto.getLink();
	}

	private TargetMessage createTargetMessage(String message) {
		return TargetMessage.builder()
				.chatId(wildberriesSearcherContainer.getChatId())
				.message(message)
				.build();
	}
}
