package com.msgrserver.socket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.msgrserver.action.Action;
import com.msgrserver.util.Mapper;
import jakarta.websocket.Encoder;
import jakarta.websocket.EndpointConfig;

public class ActionEncoder implements Encoder.Text<Action> {

    @Override
    public String encode(Action action) {
        try {
            // todo encrypt json message

            return Mapper.toJson(action);
        } catch (JsonProcessingException e) {
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