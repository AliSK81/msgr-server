package com.msgrserver.service;

import com.msgrserver.exception.BadRequestException;
import com.msgrserver.exception.ChatNotFoundException;
import com.msgrserver.exception.UserNotFoundException;
import com.msgrserver.model.entity.chat.Chat;
import com.msgrserver.model.entity.chat.PublicChat;
import com.msgrserver.model.entity.user.User;
import com.msgrserver.repository.ChatRepository;
import com.msgrserver.repository.MessageRepository;
import com.msgrserver.repository.PublicChatRepository;
import com.msgrserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PublicChatServiceImpl implements PublicChatService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final PublicChatRepository publicChatRepository;

    @Override
    public PublicChat savePublicChat(Long userId, PublicChat chat) {
        var user = findUser(userId);
        chat.setOwner(user);
        chat.setMembers(new HashSet<>(List.of(user)));
        return publicChatRepository.save(chat);
    }

    @Override
    public void deletePublicChat(Long userId, Long chatId) {
        Chat chat = findPublicChat(chatId);
        checkUserIsOwner(userId, chat);
        messageRepository.deleteMessagesByChatId(chatId);
        publicChatRepository.deleteById(chatId);
    }

    @Override
    public PublicChat joinPublicChat(Long chatId, Long userId) {
        PublicChat chat = findPublicChat(chatId);
        User user = findUser(userId);

        // todo check user is not banned
        // todo other required checks

        chat.getMembers().add(user);
        return publicChatRepository.save(chat);
    }

    @Override
    public PublicChat leavePublicChat(Long chatId, Long userId) {
        PublicChat chat = findPublicChat(chatId);
        User user = findUser(userId);
        boolean isAdmin = chat.getAdmins().contains(user);
        boolean isOwner = chat.getOwner().getId().equals(userId);
        if (isOwner) {
            deletePublicChat(userId, chatId);
        } else if (isAdmin) {
            chat.getAdmins().remove(user);
            chat.getMembers().remove(user);
        } else {
            chat.getMembers().remove(user);
        }
        return publicChatRepository.save(chat);
    }


    @Override
    public PublicChat findPublicChat(Long chatId) {
        return publicChatRepository.findById(chatId).orElseThrow(ChatNotFoundException::new);
    }

    private User findUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }


    private void checkUserIsOwner(Long userId, Chat chat) {
        User owner = ((PublicChat) chat).getOwner();
        if (!(owner.getId().equals(userId))) {
            throw new BadRequestException();
        }
    }

    private void checkChatIsPublic(Chat chat) {
        if (!(chat instanceof PublicChat)) {
            throw new BadRequestException();
        }
    }
}