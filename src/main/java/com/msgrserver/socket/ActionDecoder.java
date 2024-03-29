package com.msgrserver.socket;


import com.msgrserver.action.Action;
import com.msgrserver.util.Mapper;
import jakarta.websocket.Decoder;
import jakarta.websocket.EndpointConfig;

public class ActionDecoder implements Decoder.Text<Action> {

    @Override
    public Action decode(String jsonMessage) {
        try {
            EncryptDecrypt encryptDecrypt = new EncryptDecrypt();
            String decrypt = encryptDecrypt.decrypt(jsonMessage);
            return Mapper.fromJson(decrypt, Action.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean willDecode(String jsonMessage) {
        return true;
    }

    @Override
    public void init(EndpointConfig ec) {
    }

    @Override
    public void destroy() {
    }

}