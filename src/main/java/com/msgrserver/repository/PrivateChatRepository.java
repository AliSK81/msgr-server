package com.msgrserver.repository;

import com.msgrserver.model.entity.chat.PrivateChat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface PrivateChatRepository extends JpaRepository<PrivateChat, Long> {
    @Query(value = "SELECT * FROM msgr.private_chat WHERE " +
            "user1_id = ?1 AND user2_id = ?2 OR " +
            "user1_id = ?2 AND user2_id = ?1", nativeQuery = true)
    Optional<PrivateChat> findPrivateChatByUsersId(Long user1Id, Long user2Id);

    Set<PrivateChat> findPrivateChatsByUser1Id(Long userId);

    Set<PrivateChat> findPrivateChatsByUser2Id(Long userId);

}