package com.msgrserver.handler;

import com.msgrserver.action.Response;
import com.msgrserver.model.dto.message.MessageSendTextDto;

public interface MessageHandler {
    Response sendText(MessageSendTextDto dto);

}