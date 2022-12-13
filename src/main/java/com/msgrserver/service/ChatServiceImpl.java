package com.msgrserver.service;

import com.msgrserver.exception.ChatNotFoundException;
import com.msgrserver.exception.UserNotFoundException;
import com.msgrserver.model.entity.chat.PublicChat;
import com.msgrserver.model.entity.user.User;
import com.msgrserver.repository.MessageRepository;
import com.msgrserver.repository.PublicChatRepository;
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
    private final PublicChatRepository publicChatRepository;

    @Override
    public PublicChat findChat(Long chatId) {
        return publicChatRepository.findById(chatId).orElseThrow(ChatNotFoundException::new);
    }

    @Override
    public PublicChat savePublicChat(Long userId, PublicChat chat) {
        var user = findUser(userId);
        chat.setOwner(user);
        chat.setMembers(new HashSet<>(List.of(user)));
        return publicChatRepository.save(chat);
    }

    @Override
    public void deletePublicChat(Long userId, Long chatId) {

    }

    @Override
    public PublicChat joinPublicChat(Long chatId, Long userId) {
        var chat = findChat(chatId);
        var user = findUser(userId);

        // todo check user is not banned
        // todo other required checks

        chat.getMembers().add(user);
        return publicChatRepository.save(chat);
    }

    @Override
    public PublicChat leavePublicChat(Long chatId, Long userId) {
        return null;
    }

    private User findUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }

}
