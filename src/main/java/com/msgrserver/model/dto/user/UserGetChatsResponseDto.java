package com.msgrserver.model.dto.user;

import com.msgrserver.model.entity.chat.Chat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

@Getter
@AllArgsConstructor
public class UserGetChatsResponseDto {
    Set<Chat> chats;
}
