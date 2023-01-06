package com.msgrserver.model.dto.user;

import com.msgrserver.model.dto.ActionDto;
import lombok.*;

@Value
@Builder
@EqualsAndHashCode(callSuper = true)
public class UserDto extends ActionDto {
    private Long id;
    private String name;
    private String Avatar;
    private String username;
    private String email;
}
