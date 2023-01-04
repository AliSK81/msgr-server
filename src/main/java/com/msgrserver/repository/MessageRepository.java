package com.msgrserver.repository;

import com.msgrserver.model.entity.message.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    void deleteMessagesByChatId(Long chatId);

    Set<Message> findMessagesByChatId(Long chatId);
}