package com.msgrserver.model.dto.chat.response;

import com.msgrserver.model.dto.ActionDto;
import com.msgrserver.model.dto.user.UserDto;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@Builder
@EqualsAndHashCode(callSuper = true)
public class PrivateChatGetProfileResponseDto extends ActionDto {
    UserDto user;
}