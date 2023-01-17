package com.msgrserver.model.dto.chat;

import com.msgrserver.model.dto.ActionDto;
import com.msgrserver.model.dto.message.MessageDto;
import com.msgrserver.model.entity.chat.ChatType;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ChatDto extends ActionDto {
    private Long id;
    private Long ownerId;
    private String title;
    private ChatType type;
    private String avatar;
    private MessageDto lastMessage;
}