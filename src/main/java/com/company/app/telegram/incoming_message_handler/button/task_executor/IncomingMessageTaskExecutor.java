package com.company.app.telegram.incoming_message_handler.button.task_executor;

import com.company.app.core.exception.DeveloperMistakeException;
import com.company.app.telegram.TelegramFacade;
import com.company.app.telegram.domain.entity.IncomingMessageTask;
import com.company.app.telegram.domain.enums.ModeType;
import com.company.app.telegram.domain.repository.IncomingMessageTaskRepository;
import com.company.app.wildberries_desire_lot.domain.entity.Desire;
import com.company.app.wildberries_desire_lot.domain.repository.DesireRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IncomingMessageTaskExecutor {

    private final IncomingMessageTaskRepository incomingMessageTaskRepository;
    private final TelegramFacade telegramFacade;
    private final DesireRepository desireRepository;

    public void processIncomingMessageTask(String chatName, String modeType) {
        if (modeType.equals(ModeType.ADD_DESIRE.getType())) {
            List<IncomingMessageTask> tasks = incomingMessageTaskRepository.findAll(Specification.where(chatNameIs(chatName))
                    .and(modeTypeIs(modeType))
            );
            tasks.sort(Comparator.comparing(IncomingMessageTask::getCreateTime));

            List<Desire> desireList;
            try {
                desireList = createDesireList(chatName, tasks);
                desireRepository.saveAll(desireList);
            } catch (Exception e) {
                telegramFacade.writeToTargetChat(chatName, "Не верный формат данных");
                incomingMessageTaskRepository.deleteAll(tasks);
                return;
            }

            incomingMessageTaskRepository.deleteAll(tasks);
            desireList.forEach(desire ->
                    telegramFacade.writeToTargetChat(chatName, "Добавлено желание: артикль %s, цена %s".formatted(desire.getArticle(), desire.getPrice())));
        } else {
            throw new DeveloperMistakeException("unsupported mode type: [%s]".formatted(modeType));
        }
    }

    private List<Desire> createDesireList(String chatName, List<IncomingMessageTask> tasks) {
        List<Desire> desireList = new ArrayList<>();
        for (int i = 1; i < tasks.size(); i = i + 2) {
            String article = tasks.get(i - 1).getMessage();
            String price = tasks.get(i).getMessage();
            desireList.add(new Desire()
                    .setChatName(chatName)
                    .setPrice(new BigDecimal(price))
                    .setArticle(convertToArticle(article)));
        }
        return desireList;
    }

    static String convertToArticle(String string) {
        String[] split1 = string.split("catalog/");
        String secondPart = split1[1];
        String[] split2 = secondPart.split("/detail");
        return split2[0];
    }

    private static Specification<IncomingMessageTask> chatNameIs(String chatName) {
        return (root, criteriaQuery, criteriaBuilder) -> chatName != null ? criteriaBuilder.equal(root.get("chatName"), chatName) : null;
    }

    private static Specification<IncomingMessageTask> modeTypeIs(String modeType) {
        return (root, query, criteriaBuilder) -> modeType != null ? criteriaBuilder.equal(root.get("modeType"), modeType) : null;
    }

}
