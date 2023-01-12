package com.msgrserver.handler;

import com.msgrserver.action.Action;
import com.msgrserver.action.ActionResult;
import com.msgrserver.handler.chat.ChatHandlerImpl;
import com.msgrserver.handler.chat.PublicChatHandler;
import com.msgrserver.handler.message.MessageHandler;
import com.msgrserver.handler.user.UserHandler;
import com.msgrserver.model.dto.chat.PublicChatAddUserRequestDto;
import com.msgrserver.model.dto.chat.PublicChatJoinWithLinkRequestDto;
import com.msgrserver.model.dto.chat.PublicChatDeleteUserRequestDto;
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

    private final PublicChatHandler publicChatHandler;

    public ActionResult handle(Action action) {
        ActionResult actionResult = null;

        switch (action.getType()) {
            case SIGN_UP -> actionResult = userHandler.signUp((UserSignUpRequestDto) action.getDto());
            case SIGN_IN -> actionResult = userHandler.signIn((UserSignInRequestDto) action.getDto());
            case SEND_TEXT -> actionResult = messageHandler.sendText((MessageSendTextDto) action.getDto());
            case GET_USER_CHATS -> actionResult = userHandler.getUserChats((UserGetChatsRequestDto) action.getDto());
            case JOIN_CHAT_WITH_LINK ->
                    actionResult = publicChatHandler.joinChatWithLink((PublicChatJoinWithLinkRequestDto) action.getDto());
            case ADD_USER_BY_ADMIN -> actionResult = chatHandler.addUserToPublicChat((PublicChatAddUserRequestDto) action.getDto());
            case DELETE_USER_BY_ADMIN ->
                    actionResult = chatHandler.deleteUserFromPublicChat((PublicChatDeleteUserRequestDto) action.getDto());
        }
        return actionResult;
    }
}
