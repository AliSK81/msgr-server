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

    @Query(value = "select * from User u join Member m on u.id = m.user_id where m.chat_id = ?1", nativeQuery = true)
    Set<User> findMembersByChatId(Long chatId);

    @Query(value = "select * from User u join Admin a on u.id = a.user_id where a.chat_id = ?1", nativeQuery = true)
    Set<User> findAdminsByChatId(Long chatId);
}