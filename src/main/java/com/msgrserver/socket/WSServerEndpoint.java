package com.msgrserver.socket;

import com.msgrserver.action.Action;
import com.msgrserver.action.ActionRequest;
import com.msgrserver.action.ActionResult;
import com.msgrserver.handler.ActionHandler;
import com.msgrserver.model.entity.user.User;
import com.msgrserver.service.user.SessionService;
import com.msgrserver.socket.config.CustomSpringConfigurator;
import jakarta.annotation.PostConstruct;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import lombok.RequiredArgsConstructor;
import org.glassfish.tyrus.server.Server;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

@RequiredArgsConstructor
@Component
@ServerEndpoint(
        value = "/msgr",
        encoders = {ActionEncoder.class},
        decoders = {ActionDecoder.class},
        configurator = CustomSpringConfigurator.class
)
public class WSServerEndpoint {

    private static final Logger LOGGER = Logger.getLogger(WSServerEndpoint.class.getName());
    private final ActionHandler actionHandler;
    private final SessionService sessionService;


    private static final Map<Long, Set<Session>> sessions = new HashMap<>();


    @OnOpen
    public void onOpen(Session session) {
        LOGGER.info("[SERVER]: Handshake successful! - Connected! - Session ID: " + session.getId());
    }

    @OnMessage
    public void onMessage(Session session, Action action) {
        LOGGER.info("[FROM CLIENT]: " + action + ", Session ID: " + session.getId());

        ActionRequest request = ActionRequest.builder()
                .action(action)
                .build();

        try {
            ActionResult result = actionHandler.handle(request);

            User user = result.getUser();
            sessions.putIfAbsent(user.getId(), new HashSet<>());
            sessions.get(user.getId()).add(session);

            for (long receiverId : result.getReceivers()) {
                if (!sessions.containsKey(receiverId)){
                    continue; // offline user
                }
                sessions.get(receiverId).forEach(receiver ->
                        receiver.getAsyncRemote().sendObject(result.getAction()));
            }
        } catch (Exception ex) {
            throw ex;
        }


    }

    @OnMessage
    public void onMessage(Session session, ByteBuffer buffer) throws IOException {

    }

    @OnError
    public void onError(Session session, Throwable err) {
        LOGGER.warning("[SERVER]: Error!, Session ID: " + session.getId() + ", " + err);
        err.printStackTrace();
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        LOGGER.info("[SERVER]: Session " + session.getId() + " closed, because " + closeReason);
        sessions.values().forEach(s -> s.remove(session));
    }

    @PostConstruct
    public void start() {
        String[] serverConfig = new String[]{"localhost", "8086", "/", this.getClass().getName()};
        new Thread(() -> Server.main(serverConfig)).start();
    }

}
