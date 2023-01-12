package com.msgrserver.model.dto.message.response;

import com.msgrserver.model.dto.ActionDto;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@Builder
@EqualsAndHashCode(callSuper = true)
public class MessageSendFileResponseDto extends ActionDto {
}
