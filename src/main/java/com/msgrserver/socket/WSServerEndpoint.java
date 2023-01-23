package com.msgrserver.socket;

import com.msgrserver.action.Action;
import com.msgrserver.action.ActionResult;
import com.msgrserver.action.ActionType;
import com.msgrserver.action.handler.ActionHandler;
import com.msgrserver.exception.BadRequestException;
import com.msgrserver.exception.NotImplementedException;
import com.msgrserver.model.dto.ActionDto;
import com.msgrserver.service.user.SessionService;
import com.msgrserver.socket.config.CustomSpringConfigurator;
import jakarta.annotation.PostConstruct;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import lombok.RequiredArgsConstructor;
import org.glassfish.tyrus.server.Server;
import org.springframework.stereotype.Component;

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
    private static final Map<Long, Set<Session>> sessions = new HashMap<>();
    private final Set<ActionHandler<? extends ActionDto>> handlers;
    private final SessionService sessionService;

    @OnOpen
    public void onOpen(Session session) {
        LOGGER.info("[SERVER]: Handshake successful! - Connected! - Session ID: " + session.getId());
    }

    @OnMessage
    public void onMessage(Session session, Action action) {
        LOGGER.info("[FROM CLIENT]: " + action + ", Session ID: " + session.getId());

        Long userId = null;
        if (tokenRequired(action.getType())) {
            userId = sessionService.findUserSession(action.getToken()).getUser().getId();
        }

        ActionHandler<ActionDto> actionHandler = findHandler(action.getType());
        ActionResult result = actionHandler.handle(userId, action.getDto());

        if (!tokenRequired(action.getType())) {
            session.getAsyncRemote().sendObject(result.getAction());
        } else {
            sessions.putIfAbsent(userId, new HashSet<>());
            sessions.get(userId).add(session);
        }

        for (long receiverId : result.getReceivers()) {
            if (!sessions.containsKey(receiverId)) {
                continue; // offline user
            }
            sessions.get(receiverId).forEach(receiver ->
                    receiver.getAsyncRemote().sendObject(result.getAction()));
        }
    }

    @OnMessage
    public void onMessage(Session session, ByteBuffer buffer) {
        LOGGER.info("[FROM CLIENT]: " + "input buffer " + buffer.get(0) + ", Session ID: " + session.getId());
    }

    @OnError
    public void onError(Session session, Throwable err) {
        LOGGER.warning("[SERVER]: Error!, Session ID: " + session.getId() + ", " + err);

        Action action = Action.builder()
                .type(ActionType.ERROR)
                .build();

        if (err instanceof BadRequestException) {
            action.setError(err.getClass().getSimpleName());
        } else {
            action.setError("Server Error");
        }

        session.getAsyncRemote().sendObject(action);

        err.printStackTrace();
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        LOGGER.info("[SERVER]: Session " + session.getId() + " closed, because " + closeReason);
        sessions.values().forEach(s -> s.remove(session));
    }

    @PostConstruct
    public void start() {
        String[] serverConfig = new String[]{"localhost", "8086", "/", WSServerEndpoint.class.getName()};
        new Thread(() -> Server.main(serverConfig)).start();
    }

    @SuppressWarnings("unchecked")
    private ActionHandler<ActionDto> findHandler(ActionType actionType) {
        return handlers.stream()
                .filter(handler -> handler.type().equals(actionType))
                .findFirst()
                .map(handler -> (ActionHandler<ActionDto>) handler)
                .orElseThrow(NotImplementedException::new);
    }

    private boolean tokenRequired(ActionType actionType) {
        return !actionType.equals(ActionType.SIGN_IN) && !actionType.equals(ActionType.SIGN_UP);
    }
}
