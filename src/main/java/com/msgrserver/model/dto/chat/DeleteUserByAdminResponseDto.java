package com.msgrserver.model.dto.chat;

import com.msgrserver.model.dto.ActionDto;
import lombok.*;

@Value
@Builder
@EqualsAndHashCode(callSuper = true)
public class DeleteUserByAdminResponseDto extends ActionDto {
    Long chatId;
    Long adminId;
    Long userId;
}