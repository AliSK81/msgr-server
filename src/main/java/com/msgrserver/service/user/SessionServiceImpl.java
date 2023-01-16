package com.msgrserver.service.user;

import com.msgrserver.exception.InvalidSessionException;
import com.msgrserver.model.entity.user.UserSession;
import com.msgrserver.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {

    private final SessionRepository sessionRepository;
//    private Hibernate. session;

    public UserSession saveUserSession(UserSession userSession) {
        if (sessionRepository.findById(userSession.getId()).isPresent()) {
            throw new InvalidSessionException();
        }
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
