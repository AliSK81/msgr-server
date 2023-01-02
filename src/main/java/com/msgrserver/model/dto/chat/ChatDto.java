package com.msgrserver.model.dto.chat;

import com.msgrserver.model.dto.ActionDto;
import com.msgrserver.model.dto.message.MessageDto;
import com.msgrserver.model.entity.chat.ChatType;
import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatDto extends ActionDto {
    private Long id;
    private String title;
    private ChatType chatType;
    private String avatar;
    private MessageDto lastMessage;
}