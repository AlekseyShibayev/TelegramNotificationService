package com.company.app.telegram.integration.in.button.button_callback_action.impl.all;

import com.company.app.core.util.Collections;
import com.company.app.telegram.TelegramFacade;
import com.company.app.telegram.domain.entity.Chat;
import com.company.app.telegram.integration.in.button.button_callback_action.ButtonCallbackAction;
import com.company.app.telegram.integration.in.button.button_callback_action.model.ButtonCallbackActionContext;
import com.company.app.wildberries.desire.domain.entity.Desire;
import com.company.app.wildberries.desire.domain.repository.DesireRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class WildberriesDesireLotShowMyDesiresButtonCallbackAction implements ButtonCallbackAction {

    private static final String TYPE = "WB_DL_SHOW_MY_DESIRES";

    private final TelegramFacade telegramFacade;
    private final DesireRepository desireRepository;

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public void doAction(ButtonCallbackActionContext context) {
        Chat chat = context.getChat();
        List<Desire> allByChatName = desireRepository.findAllByChatName(chat.getChatName());

        if (Collections.isEmpty(allByChatName)) {
            telegramFacade.writeToTargetChat(chat.getChatName(), "Список желаний пуст");
        } else {
            allByChatName.forEach(desire -> telegramFacade.writeToTargetChat(desire.getChatName(), asString(desire)));
        }
    }

    private static String asString(Desire desire) {
        return desire.getArticle() + " " + desire.getPrice() + " " + desire.getDescription_();
    }

}