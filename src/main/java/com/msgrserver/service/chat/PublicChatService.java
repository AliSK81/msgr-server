package com.msgrserver.service.chat;

import com.msgrserver.model.entity.chat.PublicChat;
import com.msgrserver.model.entity.user.User;

import java.util.Set;

public interface PublicChatService {

    PublicChat findPublicChat(Long chatId);

    PublicChat createPublicChat(Long creatorId, PublicChat chat , Set<Long> initMemberIds);

    void deletePublicChat(Long userId, Long chatId);

    PublicChat joinPublicChat(Long chatId, Long userId);

    PublicChat leavePublicChat(Long chatId, Long userId);

    PublicChat addUserToPublicChat(Long chatId, Long adderId, Long userId);

    PublicChat deleteUserFromPublicChat(Long chatId, Long deleterId, Long userId);

    PublicChat selectNewAdminPublicChat(Long chatId, Long selectorId, Long userId);

    PublicChat deleteAdminPublicChat(Long chatId, Long selectorId, Long userId);

    PublicChat editProfilePublicChat(PublicChat publicChat, Long editorId);

    Set<User> getChatMembers(Long chatId);
}
