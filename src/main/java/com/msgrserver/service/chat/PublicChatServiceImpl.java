package com.msgrserver.service.chat;

import com.msgrserver.exception.*;
import com.msgrserver.model.entity.chat.Member;
import com.msgrserver.model.entity.chat.MemberId;
import com.msgrserver.model.entity.chat.PublicChat;
import com.msgrserver.model.entity.user.User;
import com.msgrserver.repository.MemberRepository;
import com.msgrserver.repository.MessageRepository;
import com.msgrserver.repository.PublicChatRepository;
import com.msgrserver.repository.UserRepository;
import com.msgrserver.util.LinkGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PublicChatServiceImpl implements PublicChatService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final PublicChatRepository publicChatRepository;
    private final MemberRepository memberRepository;


    @Override
    public PublicChat createPublicChat(Long creatorId, PublicChat chat, Set<Long> initMemberIds) {
        User creator = findUser(creatorId);
        chat.setOwner(creator);

        PublicChat createdChat = publicChatRepository.save(chat);

        Member owner = createMember(createdChat.getId(), creatorId, true, true);

        chat.setMembers(Set.of(owner));

        chat.setLink(LinkGenerator.generate(20));

        initMemberIds.forEach(id -> {
            User user = findUser(id);

            if (user.isAllowedInvite()) {
                Member member = createMember(createdChat.getId(), user.getId(), false, true);
                createdChat.getMembers().add(member);
            }
        });
        return publicChatRepository.save(chat);
    }

    private Member createMember(Long chatId, Long userId, boolean allowedInvite, boolean allowedMessage) {
        MemberId memberId = MemberId.builder()
                .chatId(chatId)
                .userId(userId)
                .build();

        return Member.builder()
                .id(memberId)
                .allowedInvite(allowedInvite)
                .allowedMessage(allowedMessage)
                .build();
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

        chat.setMembers(memberRepository.findMembersByChatId(chatId));
        MemberId memberId = MemberId.builder().userId(userId).chatId(chatId).build();
        Member member = findMember(memberId);
        chat.getMembers().add(member);

        return publicChatRepository.save(chat);
    }

    @Override
    public PublicChat leavePublicChat(Long chatId, Long userId) {
        PublicChat chat = findPublicChat(chatId);
        User user = findUser(userId);

        boolean isOwner = chat.getOwner().getId().equals(userId);

        if (isOwner) {
            deletePublicChat(chatId, userId); //todo check for replace an admin

        } else {
            Set<User> admins = userRepository.findAdminsByChatId(chatId);
            boolean isAdmin = admins.contains(user);

            if (isAdmin) {
                chat.setAdmins(admins);
                chat.getAdmins().remove(user);
            }

            chat.setMembers(memberRepository.findMembersByChatId(chatId));
            MemberId memberId = MemberId.builder().userId(userId).chatId(chatId).build();
            Member member = findMember(memberId);
            chat.getMembers().remove(member);
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

        chat.setMembers(memberRepository.findMembersByChatId(chatId));
        MemberId memberId = MemberId.builder().userId(userId).chatId(chatId).build();
        Member member = findMember(memberId);
        chat.getMembers().add(member);

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

        chat.setMembers(memberRepository.findMembersByChatId(chatId));
        MemberId memberId = MemberId.builder().userId(userId).chatId(chatId).build();
        Member member = findMember(memberId);
        chat.getMembers().remove(member);

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
        chat.setMembers(memberRepository.findMembersByChatId(chatId));
        MemberId memberId = MemberId.builder().userId(userId).chatId(chatId).build();
        Member member = findMember(memberId);

        boolean isMember = chat.getMembers().contains(member);
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
        return userRepository.findUsersByChatId(chatId);
    }


    @Override
    public PublicChat findPublicChat(Long chatId) {
        return publicChatRepository.findById(chatId).orElseThrow(ChatNotFoundException::new);
    }

    private User findUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }

    private Member findMember(MemberId memberId) {
        return memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
    }

}
