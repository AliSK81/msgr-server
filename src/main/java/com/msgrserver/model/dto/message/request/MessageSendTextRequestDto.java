package com.msgrserver.model.dto.message.request;

import com.msgrserver.model.dto.ActionDto;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@Builder
@EqualsAndHashCode(callSuper = true)
public class MessageSendTextRequestDto extends ActionDto {
    Long chatId;
    Long senderId;
    String text;
}
