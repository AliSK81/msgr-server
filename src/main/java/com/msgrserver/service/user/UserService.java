package com.msgrserver.service.user;

import com.msgrserver.model.entity.chat.Chat;
import com.msgrserver.model.entity.user.User;

import java.util.Set;

public interface UserService {
    User findUser(Long userId);

    User saveUser(User user);

    void deleteUser(Long userId);

    Set<Chat> getUserChats(Long userId);

    User findUser(String username, String password);
}
