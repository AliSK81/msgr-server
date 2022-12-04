package com.msgrserver.service;

import com.msgrserver.model.entity.chat.PrivateChat;
import com.msgrserver.model.entity.chat.PublicChat;
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
class ChatServiceTest {

    private final UserService userService;
    private final ChatServiceImpl chatService;
    private final MessageServiceImpl messageService;

    @BeforeEach
    void setUp() {
        String test = new String();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void sendTextTest() {

        var user = userService.addUser(User.builder().phone("0914").build());

        var privateChat = chatService.addChat(user.getId(),
                PrivateChat.builder()
                        .firstName("mohammad")
                        .lastName("es'hagh")
                        .build());

        var publicChat = chatService.addChat(user.getId(),
                PublicChat.builder()
                        .title("group")
                        .build());

        messageService.sendText(privateChat.getId(),
                TextMessage.builder()
                        .senderId(user.getId())
                        .text("hi").build());
    }

    @Test
    void sendFileTest() {
    }
}