package com.msgrserver.model.dto.user;

import com.msgrserver.model.dto.ActionDto;
import com.msgrserver.model.entity.chat.Chat;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserGetChatsResponseDto extends ActionDto {
    Set<Chat> chats;
}
