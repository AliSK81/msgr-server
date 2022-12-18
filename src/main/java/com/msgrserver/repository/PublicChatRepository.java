package com.msgrserver.repository;

import com.msgrserver.model.entity.chat.PrivateChat;
import com.msgrserver.model.entity.chat.PublicChat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublicChatRepository extends JpaRepository<PublicChat, Long> {
}
