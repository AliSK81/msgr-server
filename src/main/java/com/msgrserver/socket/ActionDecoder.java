package com.msgrserver.socket;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.msgrserver.action.Action;
import com.msgrserver.action.ActionType;
import com.msgrserver.util.Mapper;
import jakarta.websocket.DecodeException;
import jakarta.websocket.Decoder;
import jakarta.websocket.EndpointConfig;

public class ActionDecoder implements Decoder.Text<Action> {

    @Override
    public Action decode(String jsonMessage) throws DecodeException {
        try {

            var action = Mapper.mapToObject(jsonMessage, Action.class);
            var dtoType = action.getDtoType();



            // todo cast json to dto by dto type

            return action;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
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