package com.msgrserver.model.dto.user;

import com.msgrserver.model.dto.ActionDto;
import lombok.Builder;

@Builder
public class UserSignUpResponseDto extends ActionDto {

    private Long userId;
    private String token;
}
