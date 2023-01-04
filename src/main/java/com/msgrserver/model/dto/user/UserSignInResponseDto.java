package com.msgrserver.model.dto.user;

import com.msgrserver.model.dto.ActionDto;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@Builder
@EqualsAndHashCode(callSuper = true)
public class UserSignInResponseDto extends ActionDto {
    Long userId;
    String token;
}
