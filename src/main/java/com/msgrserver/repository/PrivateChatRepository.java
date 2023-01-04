package com.msgrserver.repository;

import com.msgrserver.model.entity.chat.PrivateChat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface PrivateChatRepository extends JpaRepository<PrivateChat, Long> {
    Set<PrivateChat> findPrivateChatsByUsersId(Long userId);
}