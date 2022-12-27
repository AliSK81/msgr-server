package com.msgrserver.service;

import com.msgrserver.exception.UserNotFoundException;
import com.msgrserver.model.entity.user.User;
import com.msgrserver.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    public User findUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public Boolean findUserByUsernameAndPassword(String username, String password) {
        return null;
    }
}
