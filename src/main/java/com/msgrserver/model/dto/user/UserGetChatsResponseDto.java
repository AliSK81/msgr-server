package com.msgrserver.model.dto.user;

import com.msgrserver.model.dto.ActionDto;
import com.msgrserver.model.dto.chat.ChatDto;
import lombok.*;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserGetChatsResponseDto extends ActionDto {
    Set<ChatDto> chats;
}
