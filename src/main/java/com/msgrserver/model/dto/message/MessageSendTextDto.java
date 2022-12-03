package com.msgrserver.model.dto.message;

import com.msgrserver.model.dto.ActionDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class MessageSendTextDto extends ActionDto {
    private Long chatId;
    private String text;
}
