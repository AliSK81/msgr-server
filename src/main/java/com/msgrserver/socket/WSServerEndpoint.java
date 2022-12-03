package com.msgrserver.socket;

import com.msgrserver.action.Action;
import com.msgrserver.action.ActionHandler;
import com.msgrserver.action.Response;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@Component
@AllArgsConstructor
@ServerEndpoint(
        value = "/msgr",
        encoders = {ActionEncoder.class},
        decoders = {ActionDecoder.class}
)
public class WSServerEndpoint {
    private static final Logger LOGGER = Logger.getLogger(WSServerEndpoint.class.getName());
    private final ActionHandler actionHandler;

    private static final Map<Long, Session> sessions = new HashMap<>();

    @OnOpen
    public void onOpen(Session session) {
        LOGGER.info("session" + session.getId() + " connected.");
        sessions.putIfAbsent(getUserId(session), session);
    }

    @OnMessage
    public void onMessage(Session session, ByteBuffer message) {

    }

    @OnMessage
    public void onMessage(Session session, Action action) {

        // todo validate session

        Response response = actionHandler.handle(action);

        for (Long userId : response.getReceivers()) {
            sessions.get(userId).getAsyncRemote().sendObject(response);
        }
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        LOGGER.info("session " + session.getId() + " closed, because " + closeReason);
        sessions.remove(getUserId(session));
    }

    @OnError
    public void onError(Session session, Throwable err) {
        LOGGER.info("[Server]: Error!, Session ID: " + session.getId() + ", " + err.getMessage());
        sessions.remove(getUserId(session));
    }

    private Long getUserId(Session session) {
        return (Long) session.getUserProperties().get("userId");
    }

}
