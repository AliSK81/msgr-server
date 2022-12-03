package com.msgrserver.repository;

import com.msgrserver.model.entity.chat.Chat;
import com.msgrserver.model.entity.message.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface ChatRepository extends JpaRepository<Chat, Long> {


}