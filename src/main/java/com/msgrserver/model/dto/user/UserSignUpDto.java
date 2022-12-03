package com.msgrserver.model.dto.user;

import com.msgrserver.model.dto.ActionDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserSignUpDto extends ActionDto {
    private String firstName;
    private String lastName;
    private String phone;
    private String avatar;
}
