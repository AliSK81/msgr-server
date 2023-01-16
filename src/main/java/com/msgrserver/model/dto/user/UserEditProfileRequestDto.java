package com.msgrserver.model.dto.user;

import com.msgrserver.model.dto.ActionDto;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)

public class UserEditProfileRequestDto extends ActionDto {
    UserDto userDto;
    boolean accessAddPublicChat;
    boolean visibleAvatar;
}