package com.msgrserver.socket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.msgrserver.action.Action;
import com.msgrserver.util.Mapper;
import jakarta.websocket.Encoder;
import jakarta.websocket.EndpointConfig;
import org.json.JSONException;
import org.json.JSONObject;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class ActionEncoder implements Encoder.Text<Action> {

    @Override
    public String encode(Action action) {
        try {
            String jsonMessage = Mapper.toJson(action);
            EncryptDecrypt encryptDecrypt = new EncryptDecrypt();
            return encryptDecrypt.encrypt(jsonMessage);
        } catch (JsonProcessingException | UnsupportedEncodingException | NoSuchPaddingException |
                 NoSuchAlgorithmException | InvalidAlgorithmParameterException | InvalidKeyException |
                 BadPaddingException | IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void init(EndpointConfig ec) {
        System.out.println("MessageEncoder - init method called");
    }

    @Override
    public void destroy() {
        System.out.println("MessageEncoder - destroy method called");
    }

}