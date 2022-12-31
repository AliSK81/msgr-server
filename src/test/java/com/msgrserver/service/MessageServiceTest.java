package com.msgrserver.service;

import com.msgrserver.model.entity.chat.ChatType;
import com.msgrserver.model.entity.chat.PublicChat;
import com.msgrserver.model.entity.message.TextMessage;
import com.msgrserver.model.entity.user.User;
import com.msgrserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class MessageServiceTest {

    private final UserService userService;
    @InjectMocks
    private final MessageService messageService;
    @Mock
    private final UserRepository userRepository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void saveTextWithoutMockTest() {

        // todo complete

        var userAli = userService.saveUser(User.builder().phone("0914").firstName("ali").build());
        var userMmd = userService.saveUser(User.builder().phone("0922").firstName("mmd").build());

        var msgFromAli = TextMessage.builder()
                .senderId(userAli.getId())
                .text("msg from ali").build();

        var sentMessage = messageService.saveText(userMmd.getId(), msgFromAli);
    }

    @Test
    void saveTextTest() {

        // todo complete

        when(userRepository.findById(1L)).thenReturn(Optional.of(User.builder().id(1L).build()));

        TextMessage textMessage = TextMessage.builder()
                .senderId(1L)
                .text("salam")
                .build();

        PublicChat publicChat = PublicChat.builder()
                .id(1L)
                .title("group")
                .type(ChatType.GROUP)
                .build();

        TextMessage savedMessage = messageService.saveText(1L , textMessage);

        assertNotNull(savedMessage.getId());
        assertNotNull(savedMessage.getDateTime());
    }

    @Test
    void saveFileTest() {
    }
}