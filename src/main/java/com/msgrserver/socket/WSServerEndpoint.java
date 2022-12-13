package com.msgrserver.socket;

import com.msgrserver.action.Action;
import com.msgrserver.util.Mapper;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashSet;
import java.util.logging.Logger;

@ServerEndpoint(
        value = "/msgr",
        encoders = {ActionEncoder.class},
        decoders = {ActionDecoder.class}
)
public class WSServerEndpoint {

    HashSet<Session> sessions = new HashSet<>();

    private static final Logger LOGGER = Logger.getLogger(WSServerEndpoint.class.getName());

    @OnOpen
    public void onOpen(Session session) {
        LOGGER.info("[SERVER]: Handshake successful! - Connected! - Session ID: " + session.getId());
        sessions.add(session);
    }

    @OnMessage
    public void onMessage(Session session, Action action) throws IOException {
        LOGGER.info("[FROM CLIENT]: " + action + ", Session ID: " + session.getId());


        System.out.println(

                Mapper.mapToJson(action)
        );

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
