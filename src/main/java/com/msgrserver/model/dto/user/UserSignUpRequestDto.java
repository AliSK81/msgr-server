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
public class UserSignUpRequestDto extends ActionDto {
    private String name;
    private String username;
    private String password;
    private String avatar;
}
