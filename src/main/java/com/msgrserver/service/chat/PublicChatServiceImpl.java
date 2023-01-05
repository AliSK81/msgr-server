package com.msgrserver.service.chat;

import com.msgrserver.exception.BadRequestException;
import com.msgrserver.exception.ChatNotFoundException;
import com.msgrserver.exception.UserNotFoundException;
import com.msgrserver.exception.UserPrivacySettingsException;
import com.msgrserver.model.entity.chat.PublicChat;
import com.msgrserver.model.entity.user.User;
import com.msgrserver.repository.MessageRepository;
import com.msgrserver.repository.PublicChatRepository;
import com.msgrserver.repository.UserRepository;
import com.msgrserver.util.LinkGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class PublicChatServiceImpl implements PublicChatService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final PublicChatRepository publicChatRepository;


    @Override
    public PublicChat savePublicChat(Long userId, PublicChat chat) {
        User user = findUser(userId);
        chat.setOwner(user);
        chat.setUsers(new HashSet<>(List.of(user)));
        chat.setLink(LinkGenerator.generate(20));
        return publicChatRepository.save(chat);
    }

    @Override
    public void deletePublicChat(Long chatId, Long userId) {
        PublicChat chat = findPublicChat(chatId);
        User user = findUser(userId);

        if (!chat.getOwner().equals(user))
            throw new BadRequestException();

        // todo check if delete users and admin is needed
        messageRepository.deleteMessagesByChatId(chatId);
        publicChatRepository.deleteById(chatId);
    }

    @Override
    public PublicChat joinPublicChat(Long chatId, Long userId) {
        PublicChat chat = findPublicChat(chatId);
        User user = findUser(userId);

        // todo check user is not banned
        // todo other required checks

        chat.setUsers(userRepository.findUsersByChatId(chatId));
        chat.getUsers().add(user);
        return publicChatRepository.save(chat);
    }

    @Override
    public PublicChat leavePublicChat(Long chatId, Long userId) {
        PublicChat chat = findPublicChat(chatId);
        User user = findUser(userId);

        boolean isOwner = chat.getOwner().getId().equals(userId);

        if (isOwner) {
            deletePublicChat(chatId, userId);

        } else {
            Set<User> admins = userRepository.findAdminsByChatId(chatId);
            boolean isAdmin = admins.contains(user);

            if (isAdmin) {
                chat.setAdmins(admins);
                chat.getAdmins().remove(user);
            }

            chat.setUsers(userRepository.findUsersByChatId(chatId));
            chat.getUsers().remove(user);
        }

        return publicChatRepository.save(chat);
    }

    @Override
    public PublicChat addUserToPublicChat(Long chatId, Long adderId, Long userId) {
        PublicChat chat = findPublicChat(chatId);
        User user = findUser(userId);
        User adder = findUser(adderId);

        boolean isAdmin = userRepository.findAdminsByChatId(chatId).contains(adder);
        boolean isOwner = chat.getOwner().equals(adder);

        if (!isOwner && !isAdmin)
            throw new BadRequestException();

        if (!user.getAccessAddPublicChat())
            throw new UserPrivacySettingsException();

        chat.setUsers(userRepository.findUsersByChatId(chatId));
        chat.getUsers().add(user);
        return publicChatRepository.save(chat);
    }

    @Override
    public PublicChat deleteUserFromPublicChat(Long chatId, Long deleterId, Long userId) {
        PublicChat chat = findPublicChat(chatId);
        User user = findUser(userId);
        User deleter = findUser(deleterId);

        Set<User> admins = userRepository.findAdminsByChatId(chatId);

        if (chat.getOwner().equals(user))
            throw new BadRequestException();

        boolean isOwner = chat.getOwner().equals(deleter);
        boolean isAdmin = admins.contains(deleter);

        if (!isOwner && !isAdmin)
            throw new BadRequestException();

        if (!isOwner && admins.contains(user))
            throw new BadRequestException();

        chat.setUsers(userRepository.findUsersByChatId(chatId));
        chat.getUsers().remove(user);

        if (admins.contains(user)) {
            chat.setAdmins(admins);
            chat.getAdmins().remove(user);
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

}
