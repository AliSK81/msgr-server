package com.msgrserver.model.dto.chat;

import com.msgrserver.model.dto.ActionDto;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@Builder
@EqualsAndHashCode(callSuper = true)
public class PublicChatSelectNewAdminRequestDto extends ActionDto {
    Long userId;
    Long selectorId;
    Long chatId;
}
