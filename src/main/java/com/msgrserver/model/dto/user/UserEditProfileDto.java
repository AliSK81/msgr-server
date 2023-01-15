package com.msgrserver.model.dto.user;

import com.msgrserver.model.dto.ActionDto;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)

public class UserEditProfileDto extends ActionDto {
    UserDto userDto;
    Boolean accessAddPublicChat;
    Boolean visibleAvatar;
}