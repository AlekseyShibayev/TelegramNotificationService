package com.company.app.telegram.integration.in.button.button_callback_actions.admin;

import java.util.List;

import com.company.app.infrastructure.jpa.entityfinder.EntityFinder;
import com.company.app.infrastructure.jpa.entityfinder.model.CommonQuery;
import com.company.app.telegram.TelegramFacade;
import com.company.app.telegram.domain.entity.Chat;
import com.company.app.telegram.integration.in.button.model.ButtonCallbackAction;
import com.company.app.telegram.integration.in.button.model.ButtonCallbackActionContext;
import com.company.app.wildberries.desire.domain.entity.Desire;
import com.company.app.wildberries.desire.domain.entity.DesireLot;
import com.company.app.wildberries.desire.domain.entity.Desire_;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class AdminWildberriesDesireLotShowButtonCallbackAction implements ButtonCallbackAction {

    private static final String TYPE = "ADMIN_WB_DL_SELECT";

    private final TelegramFacade telegramFacade;
    private final EntityFinder entityFinder;

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public void doAction(ButtonCallbackActionContext context) {
        Chat chat = context.getChat();

        List<Desire> desireList = entityFinder.findAllAsList(new CommonQuery<>(Desire.class)
            .with(Desire_.DESIRE_LOT));

        for (Desire desire : desireList) {
            DesireLot desireLot = desire.getDesireLot();
            String message = desire.getChatName() + " " + desire.getArticle() + " " + desire.getPrice() + " " + desireLot.getPrice() + " "
                + desireLot.getDescription();
            telegramFacade.writeToTargetChat(chat.getChatName(), message);
        }

    }

}
