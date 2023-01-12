package com.msgrserver.socket;

import com.msgrserver.action.Action;
import com.msgrserver.action.ActionResult;
import com.msgrserver.handler.ActionHandler;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.logging.Logger;

@Component
@RequiredArgsConstructor
@ServerEndpoint(
        value = "/msgr",
        encoders = {ActionEncoder.class},
        decoders = {ActionDecoder.class}
)
public class WSServerEndpoint {

    private final ActionHandler actionHandler;

    private static final HashMap<Long, Session> sessions = new HashMap<>();

    private static final Logger LOGGER = Logger.getLogger(WSServerEndpoint.class.getName());

    @OnOpen
    public void onOpen(Session session) {
        LOGGER.info("[SERVER]: Handshake successful! - Connected! - Session ID: " + session.getId());
        sessions.put(1L, session);
    }

    @OnMessage
    public void onMessage(Session session, Action action) throws IOException {
        LOGGER.info("[FROM CLIENT]: " + action + ", Session ID: " + session.getId());

        ActionResult response = actionHandler.handle(action);

        for (long receiverId : response.getReceivers()) {
            Session receiver = sessions.get(receiverId);
            receiver.getAsyncRemote().sendObject(response.getAction());
        }
    }

    @OnMessage
    public void onMessage(Session session, ByteBuffer buffer) throws IOException {

    }

    @OnError
    public void onError(Session session, Throwable err) {
        LOGGER.info("[SERVER]: Error!, Session ID: " + session.getId() + ", " + err.getMessage());
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        LOGGER.info("[SERVER]: Session " + session.getId() + " closed, because " + closeReason);
    }
}
