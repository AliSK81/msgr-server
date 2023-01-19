package com.msgrserver.model.dto.chat;

import com.msgrserver.model.dto.ActionDto;
import com.msgrserver.model.dto.user.UserDto;
import lombok.*;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PublicChatAddMembersResponseDto extends ActionDto {
    ChatDto chat;
    UserDto adder;
    Set<UserDto> users;
}
