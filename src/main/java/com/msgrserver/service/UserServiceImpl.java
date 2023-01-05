package com.msgrserver.service;

import com.msgrserver.exception.InvalidPasswordException;
import com.msgrserver.exception.UserNotFoundException;
import com.msgrserver.model.entity.chat.Chat;
import com.msgrserver.model.entity.user.User;
import com.msgrserver.repository.ChatRepository;
import com.msgrserver.repository.UserRepository;
import com.msgrserver.util.PasswordChecking;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final ChatRepository chatRepository;

    @Override
    public User findUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public User saveUser(User user) {
        checkUniqueUsername(user.getUsername());
        checkStrongPassword(user.getPassword());
        user.setAccessAddPublicChat(true);
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public Set<Chat> getUserChats(Long userId) {
        return chatRepository.findChatsByUsersId(userId);
    }

    @Override
    public User findUser(String username, String password) {
        return userRepository.findUserByUsernameAndPassword(username, password)
                .orElseThrow(UserNotFoundException::new);
    }

    private void checkUniqueUsername(String username) {
        userRepository.findUserByUsername(username)
                .orElseThrow(UserNotFoundException::new);
    }

    private void checkStrongPassword(String password) {
        if (!PasswordChecking.isValidPassword(password)) {
            throw new InvalidPasswordException();
        }
    }
}