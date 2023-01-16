package com.msgrserver.model.dto.message;

import com.msgrserver.model.dto.ActionDto;
import com.msgrserver.model.dto.chat.ChatDto;
import com.msgrserver.model.dto.user.UserDto;
import com.msgrserver.model.entity.message.MessageType;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MessageReceiveTextDto extends ActionDto {
    UserDto sender;
    Long chatId;
    String text;
    LocalDateTime dateTime;
    MessageType messageType;
}
