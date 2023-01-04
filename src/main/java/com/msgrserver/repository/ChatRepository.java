package com.msgrserver.repository;

import com.msgrserver.model.entity.chat.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

    Set<Chat> findChatsByUsersId(Long userId);
}
