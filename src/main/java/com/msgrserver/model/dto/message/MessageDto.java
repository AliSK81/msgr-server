package com.msgrserver.model.dto.message;

import com.msgrserver.model.dto.ActionDto;
import com.msgrserver.model.dto.user.UserDto;
import com.msgrserver.model.entity.message.MessageType;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
@EqualsAndHashCode(callSuper = true)
public class MessageDto extends ActionDto {
    Long id;
    UserDto sender;
    String text;
    String name;
    String caption;
    LocalDateTime dateTime;
    MessageType messageType;
}
