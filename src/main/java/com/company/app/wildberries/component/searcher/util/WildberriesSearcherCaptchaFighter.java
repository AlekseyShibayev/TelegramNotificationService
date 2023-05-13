package com.company.app.wildberries.component.searcher.util;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;

@Slf4j
@UtilityClass
public class WildberriesSearcherCaptchaFighter {

	private static final Random RANDOM = new Random();

	@SneakyThrows
	public static void fight(int of, int to) {
		int sleepTime = of + getRandomInt(to - of);
		log.debug("Сплю [{}] ms.", sleepTime);
		Thread.sleep(sleepTime);
	}

	private int getRandomInt(int required) {
		int n = RANDOM.nextInt(required);
		n += 1;
		return n;
	}
}
