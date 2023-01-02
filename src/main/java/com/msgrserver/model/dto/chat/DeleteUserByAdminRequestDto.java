package com.msgrserver.model.dto.chat;

import com.msgrserver.model.dto.ActionDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeleteUserByAdminRequestDto extends ActionDto {
    Long chatId;
    Long adminId;
    Long userId;
}
