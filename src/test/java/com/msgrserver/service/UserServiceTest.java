package com.msgrserver.service;

import com.msgrserver.repository.ChatRepository;
import com.msgrserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class UserServiceTest {

    private final UserRepository userRepository;
    private final ChatRepository chatRepository;

    @Test
    void findUserTest() {
    }

    @Test
    void saveUserTest() {
    }

    @Test
    void deleteUserTest() {
    }

    @Test
    void getUserChatsTest() {
    }

    @Test
    void testFindUserTest() {
    }
}