package com.msgrserver.handler.chat;

import com.msgrserver.action.ActionResult;
import com.msgrserver.model.dto.chat.PrivateChatDeleteRequestDto;
import com.msgrserver.model.dto.chat.ChatGetMessagesRequestDto;

public interface ChatHandler {
    ActionResult getChatMessages(Long userId, ChatGetMessagesRequestDto dto);

    ActionResult deletePrivateChat(Long userId, PrivateChatDeleteRequestDto dto);
}
