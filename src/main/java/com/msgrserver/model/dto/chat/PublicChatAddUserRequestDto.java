package com.msgrserver.model.dto.chat;

import com.msgrserver.model.dto.ActionDto;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@Builder
@EqualsAndHashCode(callSuper = true)
public class PublicChatAddUserRequestDto extends ActionDto {
    Long chatId;
    Long adminId;
    Long userId;
}
