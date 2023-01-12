package com.msgrserver.handler.message;

import com.msgrserver.action.ActionResult;
import com.msgrserver.model.dto.message.request.MessageSendTextRequestDto;

public interface MessageHandler {
    ActionResult sendText(MessageSendTextRequestDto dto);

}