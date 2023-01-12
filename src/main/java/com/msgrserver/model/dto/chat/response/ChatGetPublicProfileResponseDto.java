package com.msgrserver.model.dto.chat.response;

import com.msgrserver.model.dto.ActionDto;
import com.msgrserver.model.dto.user.UserDto;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.util.Set;

@Value
@Builder
@EqualsAndHashCode(callSuper = true)
public class ChatGetPublicProfileResponseDto extends ActionDto {
    Set<UserDto> members;
    Set<Long> adminIds;
    Long OwnerId;
}