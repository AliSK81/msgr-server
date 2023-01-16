package com.msgrserver.repository;

import com.msgrserver.model.entity.chat.PrivateChat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PrivateChatRepository extends JpaRepository<PrivateChat, Long> {

    Optional<PrivateChat> findPrivateChatByUser1IdAndUser2Id(Long user1Id, Long user2Id);
}