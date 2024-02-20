package com.company.app.telegram.incoming_message_handler.button.button_callback_actions.all;

import java.util.List;

import com.company.app.core.util.Collections;
import com.company.app.telegram.TelegramFacade;
import com.company.app.telegram.domain.entity.Chat;
import com.company.app.telegram.incoming_message_handler.button.model.ButtonCallbackAction;
import com.company.app.telegram.incoming_message_handler.button.model.ButtonCallbackActionContext;
import com.company.app.wildberries.desire.domain.entity.Desire;
import com.company.app.wildberries.desire.domain.entity.DesireLot;
import com.company.app.wildberries.desire.domain.repository.DesireRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


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
            telegramFacade.writeToTargetChat(chat.getChatName(), "Пусто");
        } else {
            allByChatName.forEach(desire -> telegramFacade.writeToTargetChat(desire.getChatName(), asString(desire)));
        }
    }

    private static String asString(Desire desire) {
        DesireLot desireLot = desire.getDesireLot();
        if (desireLot != null) {
            return desire.getArticle() + " " + desire.getPrice() + " " + desireLot.getDescription();
        } else {
            return desire.getArticle() + " " + desire.getPrice() + " Описания ещё нет";
        }
    }

}