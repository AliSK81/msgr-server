package com.msgrserver.model.dto.chat;

import com.msgrserver.model.dto.ActionDto;
import com.msgrserver.model.dto.user.UserDto;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PublicChatLeaveResponseDto extends ActionDto {
    Long chatId;
    UserDto user;
}
