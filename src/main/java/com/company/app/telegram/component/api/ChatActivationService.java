package com.company.app.telegram.component.api;

import com.company.app.telegram.entity.Chat;

/**
 * Отвечает за активацию чата.
 */
public interface ChatActivationService {

	void doFullActivate(Chat chat);
}
