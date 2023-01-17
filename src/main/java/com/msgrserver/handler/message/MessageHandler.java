package com.msgrserver.handler.message;

import com.msgrserver.action.ActionResult;
import com.msgrserver.model.dto.message.MessageSendTextRequestDto;

public interface MessageHandler {
    ActionResult sendText(Long senderId, MessageSendTextRequestDto dto);

}