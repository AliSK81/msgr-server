package com.msgrserver.handler.chat;

import com.msgrserver.action.ActionResult;
import com.msgrserver.model.dto.chat.request.PublicChatAddUserRequestDto;
import com.msgrserver.model.dto.chat.request.PublicChatDeleteUserRequestDto;

public interface ChatHandler {
    ActionResult addUserToPublicChat(PublicChatAddUserRequestDto dto); //todo add to publicChatHandler

    ActionResult deleteUserFromPublicChat(PublicChatDeleteUserRequestDto dto);//todo be add to publicChatHandler
}
