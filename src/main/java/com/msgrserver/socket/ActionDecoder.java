package com.msgrserver.socket;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.msgrserver.action.Action;
import com.msgrserver.action.ActionType;
import com.msgrserver.exception.BadRequestException;
import com.msgrserver.model.dto.ActionDto;
import com.msgrserver.util.Mapper;
import jakarta.websocket.Decoder;
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

public class ActionDecoder implements Decoder.Text<Action> {

    @Override
    public Action decode(String jsonMessage) {
        try {
            EncryptDecrypt encryptDecrypt = new EncryptDecrypt();
            String decrypt = encryptDecrypt.decrypt(jsonMessage);
            JSONObject obj = new JSONObject(decrypt);

            var actionTypeJson = obj.getString("type");
            var actionDtoJson = obj.getString("dto");
            var dtoNameJson = getClassName(obj.getString("dtoName"));

            var actionType = ActionType.valueOf(actionTypeJson);
            var actionDto = (ActionDto) Mapper.fromJson(actionDtoJson, dtoNameJson);

            return Action.builder()
                    .type(actionType)
                    .dto(actionDto)
                    .build();
        } catch (JsonProcessingException | JSONException | ClassNotFoundException | NoSuchAlgorithmException |
                 NoSuchPaddingException | UnsupportedEncodingException | InvalidKeyException | BadPaddingException |
                 IllegalBlockSizeException | InvalidAlgorithmParameterException e) {
            throw new RuntimeException(e);
        }
    }

    private String getClassName(String simpleName) {
        String dtoPackage = "com.msgrserver.model.dto";
        if (simpleName.startsWith("Message")) {
            return dtoPackage + ".message." + simpleName;
        } else if (simpleName.startsWith("Chat")) {
            return dtoPackage + ".chat." + simpleName;
        } else if (simpleName.startsWith("User")) {
            return dtoPackage + ".user." + simpleName;
        }
        throw new BadRequestException();
    }

    @Override
    public boolean willDecode(String jsonMessage) {
        try {
            // Check if incoming message is valid JSON
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void init(EndpointConfig ec) {
        System.out.println("MessageDecoder -init method called");
    }

    @Override
    public void destroy() {
        System.out.println("MessageDecoder - destroy method called");
    }

}