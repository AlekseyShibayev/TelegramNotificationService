package com.company.app.telegram.incoming_message_handler.button.button_callback_actions;

import com.company.app.core.util.Collections;
import com.company.app.telegram.TelegramFacade;
import com.company.app.telegram.domain.entity.Chat;
import com.company.app.telegram.incoming_message_handler.button.model.ButtonCallbackAction;
import com.company.app.telegram.incoming_message_handler.button.model.ButtonCallbackActionContext;
import com.company.app.wildberries.desire.WildberriesDesireFacade;
import com.company.app.wildberries.desire.domain.dto.FulfilledDesire;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WildberriesDesireLotShowButtonCallbackAction implements ButtonCallbackAction {

    private static final String TYPE = "WB_DL_SHOW";

    private final WildberriesDesireFacade wildberriesDesireFacade;
    private final TelegramFacade telegramFacade;

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public void doAction(ButtonCallbackActionContext context) {
        Chat chat = context.getChat();

        List<FulfilledDesire> desireList = wildberriesDesireFacade.getFulfilledDesires(chat.getChatName());

        if (Collections.isEmpty(desireList)) {
            telegramFacade.writeToTargetChat(chat.getChatName(), "Ничего не нашёл");
        } else {
            desireList.forEach(fulfilledDesire ->
                    telegramFacade.writeToTargetChat(fulfilledDesire.getChatName(), fulfilledDesire.getUrl()));
        }
    }

}