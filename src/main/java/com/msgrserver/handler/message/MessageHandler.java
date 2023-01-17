package com.msgrserver.handler.message;

import com.msgrserver.action.ActionResult;
import com.msgrserver.model.dto.message.MessageDeleteRequestDto;
import com.msgrserver.model.dto.message.MessageSendTextDto;

public interface MessageHandler {
    ActionResult sendText(MessageSendTextDto dto);

    ActionResult deleteMessage(Long userId, MessageDeleteRequestDto dto);
}