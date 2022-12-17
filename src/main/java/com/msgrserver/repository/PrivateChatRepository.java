package com.msgrserver.repository;

import com.msgrserver.model.entity.chat.PrivateChat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivateChatRepository extends JpaRepository<PrivateChat, Long> {
}