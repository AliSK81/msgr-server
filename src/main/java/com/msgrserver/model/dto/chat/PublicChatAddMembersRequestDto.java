package com.msgrserver.model.dto.chat;

import com.msgrserver.model.dto.ActionDto;
import lombok.*;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PublicChatAddMembersRequestDto extends ActionDto {
    Long chatId;
    Set<Long> userIds;
}
