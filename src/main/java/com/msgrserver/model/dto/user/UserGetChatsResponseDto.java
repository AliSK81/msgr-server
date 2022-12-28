package com.msgrserver.model.dto.user;

import com.msgrserver.model.entity.chat.Chat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserGetChatsResponseDto {
    Set<Chat> chats;
}
