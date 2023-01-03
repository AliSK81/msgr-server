package com.msgrserver.socket;

import jakarta.websocket.Session;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;

@RequiredArgsConstructor
public class SessionManager {
    private final HashMap<Long, Session> sessions = new HashMap<>();

    public void addUserSession(Long userId, Session session) {
        sessions.put(userId, session);
    }

    public Session getSession(Long userId) {
        return sessions.get(userId);
    }

    public void removeSession(Session session) {
        sessions.values().removeIf(session::equals);
    }
}
