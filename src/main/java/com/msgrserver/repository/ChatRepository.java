package com.msgrserver.repository;

import com.msgrserver.model.entity.chat.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Long> {


}