package com.msgrserver.model.dto.chat.response;

import com.msgrserver.model.dto.ActionDto;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@Builder
@EqualsAndHashCode(callSuper = true)
public class PublicChatDeleteUserResponseDto extends ActionDto {
    Long chatId;
    Long adminId;
    Long userId;
}