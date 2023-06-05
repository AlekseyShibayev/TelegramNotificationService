package com.company.app.telegram.component.api;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

import java.io.Serializable;

/**
 * документация телеграм-апи:
 * https://core.telegram.org/bots/features#inputs
 */
public interface TelegramBotConfig {

//	List<BotCommand> LIST_OF_COMMANDS = List.of(
//			new BotCommand("/tgоff", "Отключить уведомления"),
//			new BotCommand("/tgоn", "Включить уведомления"),
//			new BotCommand("/wb", "Добавить лоты wildberries (в разработке)"),
//			new BotCommand("/ex", "Последний найденный курс")
//	);

    void write(BotApiMethod<? extends Serializable> botApiMethod);

    String getName();
}
