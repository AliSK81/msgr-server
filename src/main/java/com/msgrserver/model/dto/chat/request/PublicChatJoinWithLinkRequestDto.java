package com.msgrserver.model.dto.chat.request;

import com.msgrserver.model.dto.ActionDto;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@Builder
@EqualsAndHashCode(callSuper = true)
public class PublicChatJoinWithLinkRequestDto extends ActionDto {
    Long userId;
    String link;
}
