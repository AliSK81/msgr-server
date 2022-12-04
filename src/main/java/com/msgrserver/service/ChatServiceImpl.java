package com.msgrserver.service;

import com.msgrserver.exception.BadRequestException;
import com.msgrserver.exception.ChatNotFoundException;
import com.msgrserver.exception.UserNotFoundException;
import com.msgrserver.model.entity.chat.Chat;
import com.msgrserver.model.entity.chat.PrivateChat;
import com.msgrserver.model.entity.chat.PublicChat;
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

    private void checkChat(Long chatId, Long senderId) {
        // todo check chat exist or to be allowed to send message
    }

    private Chat findChat(Long chatId) {
        return chatRepository.findById(chatId).orElseThrow(ChatNotFoundException::new);
    }

    @Override
    public Chat addChat(Long userId, Chat chat) {
        var user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        chat.setOwner(user);
        return chatRepository.save(chat);
    }

    @Override
    public void deleteChat(Long userId, Long chatId) {

    }

    @Override
    public PublicChat joinPublicChat(Long chatId, Long userId) {
        return null;
    }

    @Override
    public PublicChat leavePublicChat(Long chatId, Long userId) {
        return null;
    }
}
