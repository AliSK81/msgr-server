package com.msgrserver.model.dto.message;

import com.msgrserver.model.dto.ActionDto;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
@EqualsAndHashCode(callSuper = true)
public class MessageDto extends ActionDto {
    private Long id;
    private UserDto sender;
    private String text;
    private String name;
    private String caption;
    private LocalDateTime dateTime;
    private MessageType messageType;
}
