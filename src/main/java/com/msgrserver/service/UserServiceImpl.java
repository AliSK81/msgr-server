package com.msgrserver.service;

import com.msgrserver.exception.UserNotFoundException;
import com.msgrserver.model.entity.chat.Chat;
import com.msgrserver.model.entity.chat.PrivateChat;
import com.msgrserver.model.entity.chat.PublicChat;
import com.msgrserver.model.entity.user.User;
import com.msgrserver.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{
    private UserRepository userRepository;

    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void removeUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public User createPrivateChat(Long userId, PrivateChat privateChat) {
        return null;
    }

    @Override
    public User createPublicChat(Long userId, PublicChat publicChat) {
        return null;
    }

    @Override
    public User joinPublicChat(Long userId, Long chatId) {
        return null;
    }

    @Override
    public User leavePublicChat(Long userId, Long chatId) {
        return null;
    }

    @Override
    public void deletePublicChat(Long userId, Long chatId) {

    }


    private User findUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }

}
