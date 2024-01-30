package com.company.app.telegram.domain.repository;

import com.company.app.core.exception.DeveloperMistakeException;
import com.company.app.telegram.domain.entity.Chat;
import com.company.app.telegram.domain.enums.Role;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, Long>, JpaSpecificationExecutor<Chat> {

    default Chat findOwner() {
        return this.findOne(Specification.where((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("userInfo").get("role"), Role.OWNER.name())
        )).orElseThrow(() -> new DeveloperMistakeException("Owner must be"));
    }

    Optional<Chat> findByChatName(String chatName);

}
