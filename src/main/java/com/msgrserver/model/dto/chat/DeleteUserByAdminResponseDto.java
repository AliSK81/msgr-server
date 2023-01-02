package com.msgrserver.model.dto.chat;

import com.msgrserver.model.dto.ActionDto;
import com.msgrserver.model.entity.chat.PublicChat;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeleteUserByAdminResponseDto extends ActionDto {
    Long chatId;
    Long adminId;
    Long userId;
}