package com.msgrserver.model.dto.chat;

import com.msgrserver.model.dto.ActionDto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatRequestJoinChatWithLinkDto extends ActionDto {
    Long userId;
    String link;
}
