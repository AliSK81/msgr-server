package com.msgrserver.repository;

import com.msgrserver.model.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByUsernameAndPassword(String username, String password);

    Optional<User> findUserByUsername(String username);

    Set<User> findUsersByChatsId(Long chatId);

    @Query(value = "select * from User u left join admin on u.id = admin.user_id", nativeQuery = true)
    Set<User> findAdminsByChatId(Long chatId);
}