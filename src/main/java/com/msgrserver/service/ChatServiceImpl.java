package com.msgrserver.service;

import com.msgrserver.exception.ChatNotFoundException;
import com.msgrserver.exception.UserNotFoundException;
import com.msgrserver.model.entity.chat.Chat;
import com.msgrserver.model.entity.chat.PublicChat;
import com.msgrserver.model.entity.user.User;
import com.msgrserver.repository.ChatRepository;
import com.msgrserver.repository.MessageRepository;
import com.msgrserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ChatServiceImpl implements ChatService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final ChatRepository chatRepository;

    @Override
    public PublicChat savePublicChat(Long userId, PublicChat chat) {
        var user = findUser(userId);
        chat.setOwner(user);
        chat.setMembers(new HashSet<>(List.of(user)));
        return chatRepository.save(chat);
    }

    @Override
    public void deletePublicChat(Long userId, Long chatId) {

    }

    @Override
    public PublicChat joinPublicChat(Long chatId, Long userId) {
        var chat = (PublicChat) findChat(chatId);
        var user = findUser(userId);

        // todo check user is not banned
        // todo other required checks

        chat.getMembers().add(user);
        return chatRepository.save(chat);
    }

    @Override
    public PublicChat leavePublicChat(Long chatId, Long userId) {
        return null;
    }


    @Override
    public Chat findChat(Long chatId) {
        return chatRepository.findById(chatId).orElseThrow(ChatNotFoundException::new);
    }

    private User findUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }

}
