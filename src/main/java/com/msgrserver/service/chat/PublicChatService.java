package com.msgrserver.service.chat;

import com.msgrserver.model.entity.chat.Admin;
import com.msgrserver.model.entity.chat.Member;
import com.msgrserver.model.entity.chat.PublicChat;
import com.msgrserver.model.entity.user.User;

import java.util.Set;

public interface PublicChatService {

    PublicChat findPublicChat(Long chatId);

    Member findMember(Long chatId, Long userId);

    Admin findAdmin(Long chatId, Long userId);

    PublicChat createPublicChat(Long creatorId, PublicChat chat, Set<Long> initMemberIds);

    PublicChat joinPublicChat(Long chatId, Long userId);

    void leavePublicChat(Long chatId, Long userId);

    Set<User> addMembers(Long chatId, Long adderId, Set<Long> userIds);

    PublicChat deleteMember(Long chatId, Long deleterId, Long userId);

    Admin addAdmin(Long chatId, Long selectorId, Long userId);

    PublicChat dismissAdmin(Long chatId, Long selectorId, Long userId);

    PublicChat editProfilePublicChat(PublicChat publicChat, Long editorId);

    Set<User> getChatMembers(Long chatId);

    Set<User> getChatAdmins(Long chatId);

    public Set<User> usersCanBeAdd(Long chatId, Set<Long> userIds);
}
