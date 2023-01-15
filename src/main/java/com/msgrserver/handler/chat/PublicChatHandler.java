package com.msgrserver.handler.chat;

import com.msgrserver.action.ActionResult;
import com.msgrserver.model.dto.chat.PublicChatAddUserRequestDto;
import com.msgrserver.model.dto.chat.PublicChatDeleteUserRequestDto;
import com.msgrserver.model.dto.chat.PublicChatJoinWithLinkRequestDto;

public interface PublicChatHandler {
    ActionResult joinChatWithLink(PublicChatJoinWithLinkRequestDto dto);

    ActionResult addUserToPublicChat(PublicChatAddUserRequestDto dto);

    ActionResult deleteUserFromPublicChat(PublicChatDeleteUserRequestDto dto);
}
