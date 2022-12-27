package com.msgrserver.action;

import com.msgrserver.handler.ChatHandlerImpl;
import com.msgrserver.handler.MessageHandlerImpl;
import com.msgrserver.model.dto.message.MessageSendTextDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ActionHandler {

    private final ChatHandlerImpl chatHandler;
    private final MessageHandlerImpl messageHandler;

    public Response handle(Action action) {

        switch (action.getType()) {

            case SIGN_UP -> {

            }

            case SEND_TEXT -> {

                return messageHandler.sendText((MessageSendTextDto) action.getDto());

            }

        }

        return null;
    }

}
