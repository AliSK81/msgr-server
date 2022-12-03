package com.msgrserver.action;

import com.msgrserver.handler.ChatHandler;
import com.msgrserver.model.entity.user.User;
import com.msgrserver.model.dto.message.MessageSendTextDto;
import com.msgrserver.service.ChatService;
import com.msgrserver.service.MessageService;
import com.msgrserver.service.UserService;
import com.msgrserver.util.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ActionHandler {
    private Long userId;
    private final ChatHandler chatHandler;

    public Response handle(Action action) {

        switch (action.getType()) {

            case SIGN_UP -> {

            }

            case SEND_TEXT -> {

                return chatHandler.sendText(userId, (MessageSendTextDto) action.getDto());

            }

        }

        return null;
    }

}
