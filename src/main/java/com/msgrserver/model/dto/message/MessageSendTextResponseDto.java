package com.msgrserver.model.dto.message;

import com.msgrserver.model.dto.ActionDto;
import com.msgrserver.model.dto.chat.ChatDto;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MessageSendTextResponseDto extends ActionDto {
    ChatDto chat;
    Long receiverId;
}
