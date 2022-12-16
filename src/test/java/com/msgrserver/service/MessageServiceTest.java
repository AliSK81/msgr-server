package com.msgrserver.service;

import com.msgrserver.model.entity.message.TextMessage;
import com.msgrserver.model.entity.user.User;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class MessageServiceTest {

    private final UserService userService;
    private final ChatService chatService;
    private final MessageService messageService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void saveTextTest() {
        var userAli = userService.saveUser(User.builder().phone("0914").firstName("ali").build());
        var userMmd = userService.saveUser(User.builder().phone("0922").firstName("mmd").build());

        var msgFromAli = TextMessage.builder()
                .senderId(userAli.getId())
                .text("msg from ali").build();

        var sentMessage = messageService.saveText(userMmd.getId(), msgFromAli);
    }

    @Test
    void saveFileTest() {
    }
}