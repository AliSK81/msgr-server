package com.msgrserver.handler.chat;

import com.msgrserver.action.ActionResult;
import com.msgrserver.model.dto.chat.PublicChatAddUserRequestDto;
import com.msgrserver.model.dto.chat.PublicChatDeleteUserRequestDto;

public interface ChatHandler {
    ActionResult addUserToPublicChat(PublicChatAddUserRequestDto dto); //todo add to publicChatHandler

    ActionResult deleteUserFromPublicChat(PublicChatDeleteUserRequestDto dto);//todo be add to publicChatHandler
}
