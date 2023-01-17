package com.msgrserver.repository;

import com.msgrserver.exception.ChatNotFoundException;
import com.msgrserver.model.entity.chat.PublicChat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface PublicChatRepository extends JpaRepository<PublicChat, Long> {
    PublicChat findPublicChatByLink(String link) throws ChatNotFoundException;

    Set<PublicChat> findPublicChatsByMembersId(Long userId);
}
