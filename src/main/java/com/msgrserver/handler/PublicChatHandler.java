package com.msgrserver.handler;

import com.msgrserver.action.Response;
import com.msgrserver.model.dto.chat.ChatJoinWithLinkRequestDto;

public interface PublicChatHandler {
    Response joinChatWithLink(ChatJoinWithLinkRequestDto dto);
}
