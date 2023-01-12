package com.msgrserver.model.dto.user;

import com.msgrserver.model.dto.ActionDto;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@Builder
@EqualsAndHashCode(callSuper = true)
public class UserDto extends ActionDto {
    Long id;
    String phone;
    String email;
    String username;
    String name;
    String avatar;
}
