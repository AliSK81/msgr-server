package com.msgrserver.model.dto.message;

import com.msgrserver.model.dto.user.UserDto;
import com.msgrserver.model.entity.message.MessageType;

import java.time.LocalDateTime;

public class MessageDto {
    private Long id;
    private UserDto sender;
    private String text;
    private String name;
    private String caption;
    private LocalDateTime dateTime;
    private MessageType messageType;
}
