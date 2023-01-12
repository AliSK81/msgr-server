package com.msgrserver.handler.chat;

import com.msgrserver.action.ActionResult;
import com.msgrserver.model.dto.chat.request.PublicChatJoinWithLinkRequestDto;

public interface PublicChatHandler {
    ActionResult joinChatWithLink(PublicChatJoinWithLinkRequestDto dto);
}
