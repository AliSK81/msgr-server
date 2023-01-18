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
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PublicChatServiceImpl implements PublicChatService {

    private final UserRepository userRepository;
    private final PublicChatRepository publicChatRepository;
    private final ChatService chatService;

    @Override
    public PublicChat createPublicChat(Long creatorId, PublicChat chat, Set<Long> initMemberIds) {
        User creator = findUser(creatorId);
        chat.setOwner(creator);
        chat.setMembers(new HashSet<>(List.of(creator)));
        chat.setLink(LinkGenerator.generate(20));
        chat.setCreatedAt(System.currentTimeMillis());
        initMemberIds.forEach(id -> {
            User user = findUser(id);
            if (user.isAllowedInvite()) {
                chat.getMembers().add(user);
            }
        });
        return publicChatRepository.save(chat);
    }

    @Override
    public PublicChat joinPublicChat(Long chatId, Long userId) {
        PublicChat chat = findPublicChat(chatId);
        User user = findUser(userId);

        // todo check user is not banned
        // todo other required checks

        chat.setMembers(userRepository.findMembersByChatId(chatId));
        chat.getMembers().add(user);
        return publicChatRepository.save(chat);
    }

    @Override
    public PublicChat leavePublicChat(Long chatId, Long userId) {
        PublicChat chat = findPublicChat(chatId);
        User user = findUser(userId);

        boolean isOwner = chat.getOwner().getId().equals(userId);

        if (isOwner) {
            chatService.deleteChat(chatId, userId);//todo check for replace an admin

        } else {
            Set<User> admins = userRepository.findAdminsByChatId(chatId);
            boolean isAdmin = admins.contains(user);

            if (isAdmin) {
                chat.setAdmins(admins);
                chat.getAdmins().remove(user);
            }

            chat.setMembers(userRepository.findMembersByChatId(chatId));
            chat.getMembers().remove(user);
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

        if (!user.isAllowedInvite())
            throw new UserPrivacySettingsException();
        chat.setMembers(userRepository.findMembersByChatId(chatId));
        chat.getMembers().add(user);
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

        chat.setMembers(userRepository.findMembersByChatId(chatId));
        chat.getMembers().remove(user);

        if (admins.contains(user)) {
            chat.setAdmins(admins);
            chat.getAdmins().remove(user);
        }

        return publicChatRepository.save(chat);
    }

    @Override
    public PublicChat selectNewAdminPublicChat(Long chatId, Long selectorId, Long userId) {
        PublicChat chat = findPublicChat(chatId);
        User user = findUser(userId);
        User selector = findUser(selectorId);
        chat.setAdmins(userRepository.findAdminsByChatId(chatId));
        chat.setMembers(userRepository.findMembersByChatId(chatId));
        boolean isMember = chat.getMembers().contains(user);
        boolean isAdmin = chat.getAdmins().contains(user);
        boolean isOwner = chat.getOwner().equals(user);

        if (!chat.getOwner().equals(selector))
            throw new BadRequestException();

        if (!isMember)
            throw new BadRequestException();

        if (isOwner || isAdmin)
            throw new BadRequestException();

        chat.getAdmins().add(user);
        return publicChatRepository.save(chat);
    }

    @Override
    public PublicChat deleteAdminPublicChat(Long chatId, Long selectorId, Long adminId) {
        PublicChat chat = findPublicChat(chatId);
        User admin = findUser(adminId);
        User selector = findUser(selectorId);
        chat.setAdmins(userRepository.findAdminsByChatId(chatId));

        if (!chat.getOwner().equals(selector))
            throw new BadRequestException();

        if (chat.getOwner().equals(admin))
            throw new BadRequestException();

        boolean isAdmin = chat.getAdmins().contains(admin);

        if (!isAdmin)
            throw new BadRequestException();

        chat.getAdmins().remove(admin);
        return publicChatRepository.save(chat);
    }

    @Override
    public PublicChat editProfilePublicChat(PublicChat publicChat, Long editorId) {
        PublicChat chat = publicChatRepository.getReferenceById(publicChat.getId());
        Set<User> admins = userRepository.findAdminsByChatId(chat.getId());
        Set<Long> adminIds = admins.stream().map(User::getId).collect(Collectors.toSet());
        boolean isAdmin = adminIds.contains(editorId);
        boolean isOwner = chat.getOwner().getId().equals(editorId);
        if (!isAdmin && !isOwner) {
            throw new BadRequestException();
        } else {
            chat.setAvatar(publicChat.getAvatar());
            chat.setTitle(publicChat.getTitle());
        }
        return publicChatRepository.save(chat);
    }

    @Override
    public Set<User> getChatMembers(Long chatId) {
        return userRepository.findMembersByChatId(chatId);
    }


    @Override
    public PublicChat findPublicChat(Long chatId) {
        return publicChatRepository.findById(chatId).orElseThrow(ChatNotFoundException::new);
    }

    private User findUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }

}
