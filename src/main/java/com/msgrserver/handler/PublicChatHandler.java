package com.msgrserver.handler;

import com.msgrserver.action.Response;
import com.msgrserver.model.dto.chat.ChatRequestJoinChatWithLinkDto;

public interface PublicChatHandler {
    Response joinChatWithLink(ChatRequestJoinChatWithLinkDto dto);
}
