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
    String senderName;
    String text;
    LocalDateTime dateTime;
}
