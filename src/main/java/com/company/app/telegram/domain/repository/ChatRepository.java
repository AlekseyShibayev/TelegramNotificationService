package com.company.app.telegram.domain.repository;

import com.company.app.telegram.domain.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, Long> {

    boolean existsChatByChatName(String chatName);

    Optional<Chat> findFirstByChatName(String chatName);

}
