package com.msgrserver.model.dto.chat;

import com.msgrserver.model.dto.ActionDto;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PublicChatSelectNewAdminRequestDto extends ActionDto {
    Long userId;
    Long chatId;
}
