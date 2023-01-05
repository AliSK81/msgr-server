package com.msgrserver.service;

import com.msgrserver.model.entity.chat.Chat;
import com.msgrserver.model.entity.chat.PrivateChat;
import com.msgrserver.model.entity.message.TextMessage;
import com.msgrserver.model.entity.user.User;
import com.msgrserver.repository.ChatRepository;
import com.msgrserver.repository.MessageRepository;
import com.msgrserver.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class MessageServiceTest {

    @InjectMocks
    private MessageServiceImpl messageService;

    @Mock
    private MessageRepository messageRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ChatRepository chatRepository;

    @Test
    void saveTextInPrivateChatTest() {
        User user = User.builder().id(1L).build();
        Chat chat = PrivateChat.builder().id(1L).build();

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(chatRepository.findById(chat.getId())).thenReturn(Optional.of(chat));
        when(messageRepository.save(any(TextMessage.class))).thenAnswer(i -> i.getArgument(0));

        TextMessage message = TextMessage.builder().text("salam").build();

        TextMessage savedText = messageService.saveText(chat.getId(), user.getId(), message);

        assertNotNull(savedText.getDateTime());
        verify(messageRepository).save(savedText);
    }

    @Test
    void saveFile() {
    }

    @Test
    void getLastMessage() {
    }
}