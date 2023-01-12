package com.msgrserver.model.dto.user.request;

import com.msgrserver.model.dto.ActionDto;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@Builder
@EqualsAndHashCode(callSuper = true)
public class UserGetChatsRequestDto extends ActionDto {
    Long userId;
}
