package com.msgrserver.service;

import com.msgrserver.model.entity.chat.Chat;
import com.msgrserver.model.entity.chat.PrivateChat;
import com.msgrserver.model.entity.chat.PublicChat;
import com.msgrserver.model.entity.user.User;

import java.util.List;
import java.util.Set;

public interface UserService {
    User addUser(User user);

    void removeUser(Long userId);

    User createPrivateChat(Long userId, PrivateChat privateChat);

    User createPublicChat(Long userId, PublicChat publicChat);

    User joinPublicChat(Long userId, Long chatId);

    User leavePublicChat(Long userId, Long chatId);

    void deletePublicChat(Long userId, Long chatId);

}
