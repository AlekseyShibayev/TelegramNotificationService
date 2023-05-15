package com.company.app.telegram.domain.repository;

import com.company.app.telegram.domain.entity.Chat;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRepository extends CrudRepository<Chat, Long> {

	@EntityGraph(value = "Chat.all")
	List<Chat> findAll();

	boolean existsChatByChatName(String chatName);

	@EntityGraph(value = "Chat.all")
	Optional<Chat> findFirstByChatName(String chatName);

	@EntityGraph(value = "Chat.all")
	Optional<Chat> findById(Long id);
}
