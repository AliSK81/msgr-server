package com.msgrserver.model.dto.message;

import com.msgrserver.model.dto.ActionDto;

import java.time.LocalDateTime;

public class MessageDto extends ActionDto {
    private String senderName;
    private String text;
    private LocalDateTime dateTime;
}
