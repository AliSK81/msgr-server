package com.msgrserver.model.dto.chat;

import com.msgrserver.model.dto.ActionDto;
import com.msgrserver.model.dto.message.MessageDto;
import com.msgrserver.model.entity.chat.ChatType;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.util.Set;

@Value
@Builder
@EqualsAndHashCode(callSuper = true)
public class ChatDto extends ActionDto {
    Long id;
    ChatType type;
    String title;
    String link;
    String avatar;
    boolean allowedInvite;
    MessageDto lastMessage;
}