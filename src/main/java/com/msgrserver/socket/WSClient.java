package com.msgrserver.socket;

import com.msgrserver.action.Action;
import com.msgrserver.action.ActionType;
import com.msgrserver.model.dto.message.request.MessageSendTextRequestDto;
import jakarta.websocket.*;
import org.glassfish.tyrus.client.ClientManager;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Logger;

@ClientEndpoint(
        encoders = {ActionEncoder.class},
        decoders = {ActionDecoder.class}
)
public class WSClient {

    private static final java.util.logging.Logger LOGGER = Logger.getLogger(WSClient.class.getName());
    private static CountDownLatch latch;

    public static void main(String[] args) {
        latch = new CountDownLatch(1);
        ClientManager clientManager = ClientManager.createClient();
        try {
            URI uri = new URI("ws://localhost:8086/msgr");
            clientManager.connectToServer(WSClient.class, uri);
            latch.await();
        } catch (URISyntaxException | DeploymentException | InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @OnOpen
    public void onOpen(Session session) throws IOException {

        LOGGER.info("[CLIENT]: Connected to server... \n[CLIENT]: Session ID: " + session.getId());
        try {

            MessageSendTextRequestDto msgFromAli = MessageSendTextRequestDto.builder()
                    .senderId(1L)
                    .chatId(2L)
                    .text("msg from ali").build();


            Action action = Action.builder()
                    .type(ActionType.SEND_TEXT)
                    .dto(msgFromAli)
                    .build();


            session.getBasicRemote().sendObject(action);

        } catch (IOException | EncodeException e) {
            e.printStackTrace();
        }

    }

    @OnMessage
    public void onMessage(Session session, Action action) {
        LOGGER.info("[FROM SERVER]: " + action + ", Session ID: " + session.getId());
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        LOGGER.info("[CLIENT]: Session " + session.getId() + " close, because " + closeReason);
        latch.countDown();
    }

    @OnError
    public void onError(Session session, Throwable err) {
        LOGGER.info("[CLIENT]: Error!, Session ID: " + session.getId() + ", " + err.getMessage());
    }

}