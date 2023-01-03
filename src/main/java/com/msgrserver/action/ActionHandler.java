package com.msgrserver.action;

import com.msgrserver.handler.ChatHandlerImpl;
import com.msgrserver.handler.MessageHandler;
import com.msgrserver.handler.UserHandler;
import com.msgrserver.model.dto.message.MessageSendTextDto;
import com.msgrserver.model.dto.user.UserGetChatsRequestDto;
import com.msgrserver.model.dto.user.UserSignInRequestDto;
import com.msgrserver.model.dto.user.UserSignUpRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ActionHandler {

    private final ChatHandlerImpl chatHandler;
    private final MessageHandler messageHandler;
    private final UserHandler userHandler;

    public Response handle(Action action) {
        Response response = null;

        switch (action.getType()) {
            case SIGN_UP -> response = userHandler.signUp((UserSignUpRequestDto) action.getDto());
            case SIGN_IN -> response = userHandler.signIn((UserSignInRequestDto) action.getDto());
            case SEND_TEXT -> response = messageHandler.sendText((MessageSendTextDto) action.getDto());
            case GET_USER_CHATS -> response = userHandler.getUserChats((UserGetChatsRequestDto) action.getDto());
        }
        return response;
    }
}
