package com.msgrserver.model.dto.message.response;

import com.msgrserver.model.dto.ActionDto;
import com.msgrserver.model.entity.message.MessageType;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
@EqualsAndHashCode(callSuper = true)
public class MessageSendTextResponseDto extends ActionDto {
    Long senderId;
    Long chatId;
    String text;
    LocalDateTime dateTime;
    MessageType messageType;
}
