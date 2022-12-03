package com.msgrserver.model.dto.message;

import com.msgrserver.model.dto.ActionDto;
import com.msgrserver.model.entity.message.MessageType;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
public class MessageReceiveTextDto extends ActionDto {
    private Long senderId;
    private Long chatId;
    private String text;
    private LocalDateTime dateTime;
    private MessageType messageType;
}
