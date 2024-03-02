package com.company.app.telegram.domain.repository;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import com.company.app.telegram.domain.entity.Chat;
import com.company.app.telegram.domain.enums.Role;
import com.company.app.telegram.domain.spec.ChatSpecification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface ChatRepository extends JpaRepository<Chat, Long>, JpaSpecificationExecutor<Chat> {

    default Chat findOwner() {
        return this.findOne(ChatSpecification.roleIs(Role.OWNER))
            .orElseThrow(() -> new EntityNotFoundException("Owner must be"));
    }

    Optional<Chat> findByChatName(String chatName);

}
