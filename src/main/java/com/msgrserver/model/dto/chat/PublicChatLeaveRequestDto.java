package com.msgrserver.model.dto.chat;

import com.msgrserver.model.dto.ActionDto;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PublicChatLeaveRequestDto extends ActionDto {
    Long chatId;
}
