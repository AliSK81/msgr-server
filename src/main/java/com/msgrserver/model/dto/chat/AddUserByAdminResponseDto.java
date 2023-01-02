package com.msgrserver.model.dto.chat;

import com.msgrserver.model.dto.ActionDto;
import com.msgrserver.model.entity.chat.PublicChat;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddUserByAdminResponseDto extends ActionDto {
    private Long chatId;
    private Long adminId;
    private Long userId;
}
