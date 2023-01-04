package com.msgrserver.model.dto.user;

import com.msgrserver.model.dto.ActionDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto extends ActionDto {
    private Long id;
    private String name;
    private String Avatar;
    private String username;
    private String email;
}
