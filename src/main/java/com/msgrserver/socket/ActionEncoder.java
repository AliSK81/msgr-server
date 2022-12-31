package com.msgrserver.socket;

import com.msgrserver.action.Action;
import com.msgrserver.util.Mapper;
import jakarta.websocket.Encoder;
import jakarta.websocket.EndpointConfig;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ActionEncoder implements Encoder.Text<Action> {

    private final Mapper mapper;

    @Override
    public String encode(Action action) {
        try {
            String jsonMessage = mapper.toJson(action);
            EncryptDecrypt encryptDecrypt = new EncryptDecrypt();
            return encryptDecrypt.encrypt(jsonMessage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void init(EndpointConfig ec) {
    }

    @Override
    public void destroy() {
    }

}