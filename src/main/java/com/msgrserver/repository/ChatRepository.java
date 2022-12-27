package com.msgrserver.repository;

import com.msgrserver.model.entity.chat.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    Set<Chat> findUserChatsByID(Long userId);
}
