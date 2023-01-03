package com.msgrserver.model.dto.user;

import com.msgrserver.model.dto.ActionDto;
import com.msgrserver.model.dto.chat.ChatReceiveProfileUpdateDto;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.util.Set;

@Value
@Builder
@EqualsAndHashCode(callSuper = true)
public class UserReceiveUpdateDto extends ActionDto {

    Set<ChatReceiveProfileUpdateDto> chat;
}
