package com.msgrserver.model.dto.user;

import lombok.Builder;

@Builder
public class UserCheckSignUpDto {

    private String username;
    private String password;
}
