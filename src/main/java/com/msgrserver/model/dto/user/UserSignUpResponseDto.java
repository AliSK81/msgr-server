package com.msgrserver.model.dto.user;

import com.msgrserver.model.dto.ActionDto;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@Builder
@EqualsAndHashCode(callSuper = true)
public class UserSignUpResponseDto extends ActionDto {
    Long userId;
}
