package com.msgrserver.repository;

import com.msgrserver.model.entity.message.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    void deleteMessagesByChatId(Long chatId);

    Set<Message> findMessagesByChatId(Long chatId);

    @Query(value = "SELECT m FROM Message m WHERE m.chat.id = ?1 " +
            "AND m.date = (SELECT MAX(date) FROM Message WHERE chat.id = ?1)")
    Message getLastMessageByChatId(Long chatId);
}