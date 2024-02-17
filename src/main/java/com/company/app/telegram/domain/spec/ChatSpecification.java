package com.company.app.telegram.domain.spec;

import com.company.app.telegram.domain.entity.Chat;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;

import java.util.Set;

@UtilityClass
public class ChatSpecification {

    public static Specification<Chat> chatNameIs(String chatName) {
        return (r, cq, cb) -> cb.equal(r.get("chatName"), chatName);
    }

    public static Specification<Chat> chatNameIn(Set<String> chatNames) {
        return (r, cq, cb) -> r.get("chatName").in(chatNames);
    }

}
