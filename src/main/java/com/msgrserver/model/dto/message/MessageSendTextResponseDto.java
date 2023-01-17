package com.msgrserver.model.dto.message;

import com.msgrserver.model.dto.ActionDto;
import com.msgrserver.model.dto.chat.ChatDto;
import com.msgrserver.model.dto.user.UserDto;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MessageSendTextResponseDto extends ActionDto {
    ChatDto chat;
    UserDto user;
    MessageDto message;
}
