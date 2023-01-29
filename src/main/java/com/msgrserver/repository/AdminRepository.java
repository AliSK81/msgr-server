package com.msgrserver.repository;

import com.msgrserver.model.entity.chat.Admin;
import com.msgrserver.model.entity.chat.AdminId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface AdminRepository extends JpaRepository<Admin, AdminId> {
    Set<Admin> findAdminsByIdChatId(Long chatId);

    void deleteByIdChatId(Long chatId);

    Optional<Admin> findByChatIdAndUserId(Long chatId, Long userId);

}
