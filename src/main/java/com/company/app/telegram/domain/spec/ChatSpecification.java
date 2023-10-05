package com.company.app.telegram.domain.spec;

import com.company.app.telegram.domain.entity.Chat;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;

@UtilityClass
public class ChatSpecification {

    public static Specification<Chat> chatNameIs(String chatName) {
        return (r, cq, cb) -> chatName != null ? cb.equal(r.get("chatName"), chatName) : null;
    }

}
