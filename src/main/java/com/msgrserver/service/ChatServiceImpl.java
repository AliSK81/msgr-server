package com.msgrserver.service;

import com.msgrserver.exception.BadRequestException;
import com.msgrserver.exception.ChatNotFoundException;
import com.msgrserver.model.entity.chat.Chat;
import com.msgrserver.model.entity.message.BinaryMessage;
import com.msgrserver.model.entity.message.Message;
import com.msgrserver.model.entity.message.TextMessage;
import com.msgrserver.repository.ChatRepository;
import com.msgrserver.repository.MessageRepository;
import com.msgrserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class ChatServiceImpl implements ChatService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    private final ChatRepository chatRepository;

    @Override
    public Chat sendText(Long chatId, Long senderId, TextMessage textMessage) {

        checkChat(chatId, senderId);

        textMessage.setSenderId(senderId);
        textMessage.setDateTime(LocalDateTime.now());

        var chat = findChat(chatId);
        chat.getMessages().add(textMessage);

        return chatRepository.save(chat);
    }

    @Override
    public Chat sendFile(Long chatId, BinaryMessage binaryMessage) {
        return null;
    }


    private void checkChat(Long chatId, Long senderId) {
        // todo check chat exist or to be allowed to send message
    }

    private Chat findChat(Long chatId) {
        return chatRepository.findById(chatId).orElseThrow(ChatNotFoundException::new);
    }
}
