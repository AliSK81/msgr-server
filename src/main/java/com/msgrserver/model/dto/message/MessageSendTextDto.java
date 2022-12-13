package com.msgrserver.model.dto.message;

import com.msgrserver.model.dto.ActionDto;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageSendTextDto extends ActionDto {
    private Long chatId;
    private Long senderId;
    private String text;
}
