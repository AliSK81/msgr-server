package com.msgrserver.repository;

import com.msgrserver.exception.ChatNotFoundException;
import com.msgrserver.model.entity.chat.PublicChat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublicChatRepository extends JpaRepository<PublicChat, Long> {
    PublicChat findPublicChatByLink(String link) throws ChatNotFoundException;
}
