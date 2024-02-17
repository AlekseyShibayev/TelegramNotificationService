package com.company.app.telegram.domain.spec;

import com.company.app.telegram.domain.entity.IncomingMessageTask;
import com.company.app.telegram.domain.entity.IncomingMessageTask_;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;

@UtilityClass
public class IncomingMessageTaskSpecification {

    public static Specification<IncomingMessageTask> chatNameIs(String chatName) {
        return (r, cq, cb) -> cb.equal(r.get(IncomingMessageTask_.CHAT_NAME), chatName);
    }

    public static Specification<IncomingMessageTask> modeTypeIs(String modeType) {
        return (r, cq, cb) -> cb.equal(r.get(IncomingMessageTask_.MODE_TYPE), modeType);
    }

}
