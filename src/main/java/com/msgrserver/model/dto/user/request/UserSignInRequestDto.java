package com.msgrserver.model.dto.user.request;

import com.msgrserver.model.dto.ActionDto;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@Builder
@EqualsAndHashCode(callSuper = true)
public class UserSignInRequestDto extends ActionDto {
    String username;
    String password;
}
