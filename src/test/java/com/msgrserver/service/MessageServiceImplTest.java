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
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
@RequiredArgsConstructor
class MessageServiceImplTest {

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
    void saveTextTest() {

        User sender = User.builder().id(1L).build();
        when(userRepository.findById(1L)).thenReturn(Optional.of(sender));

        TextMessage textMessage = TextMessage.builder()
                .sender(sender)
                .text("salam")
                .build();

        PublicChat chat = PublicChat.builder()
                .id(1L)
                .title("group")
                .type(ChatType.GROUP)
                .build();

        TextMessage savedMessage = messageService.saveText(chat.getId(), sender.getId(), textMessage);

        assertNotNull(savedMessage.getId());
        assertNotNull(savedMessage.getDateTime());
    }

    @Test
    void saveFileTest() {
    }
}