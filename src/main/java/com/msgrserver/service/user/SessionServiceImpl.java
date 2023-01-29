package com.msgrserver.service.user;

import com.msgrserver.exception.InvalidSessionException;
import com.msgrserver.model.entity.user.User;
import com.msgrserver.model.entity.user.UserSession;
import com.msgrserver.repository.SessionRepository;
import com.msgrserver.util.TokenGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {

    private final SessionRepository sessionRepository;

    public UserSession createUserSession(User user) {

        String sessionId = TokenGenerator.generateNewToken();

        if (sessionRepository.findById(sessionId).isPresent()) {
            // todo generate another unique token instead of exception
            throw new InvalidSessionException();
        }

        UserSession userSession = UserSession.builder()
                .id(sessionId)
                .user(user)
                .build();

        return sessionRepository.save(userSession);
    }

    public UserSession findUserSession(String sessionId) {
        return sessionRepository.findById(sessionId).orElseThrow(InvalidSessionException::new);
    }

    @Override
    public Set<UserSession> findUserSessions(Long userId) {
        return sessionRepository.findUserSessionsByUserId(userId);
    }

    public void deleteUserSession(String sessionId) {
        sessionRepository.deleteById(sessionId);
    }
}
