package com.company.app.wildberries_searcher.data;

import com.company.app.telegram.controller.TelegramController;
import com.company.app.telegram.domain.dto.TargetMessage;
import com.company.app.wildberries_desire_lot.domain.dto.WildberriesLinkDto;
import com.company.app.wildberries_searcher.api.WildberriesSearcher;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@Builder
public class WildberriesSearcherTask implements Runnable {

	private WildberriesSearcherContainer wildberriesSearcherContainer;
	private WildberriesSearcher wildberriesSearcher;
	private TelegramController telegramController;
	private WildberriesSearcherCallback callBack;

	@Override
	public void run() {
		try {
			doWork();
		} finally {
			callBack.callback();
		}
	}

	private void doWork() {
		List<WildberriesLinkDto> result = getAllProducts();
		telegramController.say(createTargetMessage(String.format("Завершен поиск: %s", wildberriesSearcherContainer)));
		result.stream()
				.map(this::createMessage)
				.distinct()
				.map(this::createTargetMessage)
				.forEach(targetMessage -> telegramController.say(targetMessage));
	}

	private List<WildberriesLinkDto> getAllProducts() {
		List<WildberriesLinkDto> result = new ArrayList<>();
		Arrays.stream(wildberriesSearcherContainer.getSupplier().split(";"))
				.forEach(supplier -> {
					wildberriesSearcherContainer.setSupplier(supplier);
					result.addAll(wildberriesSearcher.search(this.wildberriesSearcherContainer));
				});
		return result;
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
