package com.msgrserver.service.user;

import com.msgrserver.model.entity.user.User;
import com.msgrserver.model.entity.user.UserSession;

import java.util.Set;

public interface SessionService {

    UserSession createUserSession(User user);

    UserSession findUserSession(String sessionId);

    Set<UserSession> findUserSessions(Long userId);

    void deleteUserSession(String sessionId);
}
