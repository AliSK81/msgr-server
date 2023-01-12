package com.msgrserver.model.dto.user.response;

import com.msgrserver.model.dto.ActionDto;
import com.msgrserver.model.dto.chat.ChatDto;
import com.msgrserver.model.dto.message.MessageDto;
import com.msgrserver.model.dto.user.UserDto;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.util.Set;

@Value
@Builder
@EqualsAndHashCode(callSuper = true)
public class UserGetChatsResponseDto extends ActionDto {
    Set<ChatDto> chats;
}
