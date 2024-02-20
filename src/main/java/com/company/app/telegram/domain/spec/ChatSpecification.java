package com.company.app.telegram.domain.spec;

import java.util.Set;

import com.company.app.telegram.domain.entity.Chat;
import com.company.app.telegram.domain.entity.Chat_;
import com.company.app.telegram.domain.entity.UserInfo_;
import com.company.app.telegram.domain.enums.Role;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;


@UtilityClass
public class ChatSpecification {

    public static Specification<Chat> chatNameIs(String chatName) {
        return (r, cq, cb) -> cb.equal(r.get(Chat_.CHAT_NAME), chatName);
    }

    public static Specification<Chat> chatNameIn(Set<String> chatNames) {
        return (r, cq, cb) -> r.get(Chat_.CHAT_NAME).in(chatNames);
    }

    public static Specification<Chat> roleIs(Role role) {
        return (r, cq, cb) -> cb.equal(r.get(Chat_.USER_INFO).get(UserInfo_.ROLE), role.name());
    }

}