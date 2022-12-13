package com.msgrserver.service;

import com.msgrserver.model.entity.user.User;

public interface UserService {
    User findUser(Long userId);

    User saveUser(User user);

    void deleteUser(Long userId);


}
