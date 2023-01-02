package com.msgrserver.handler;

import com.msgrserver.action.Response;
import com.msgrserver.model.dto.chat.AddUserByAdminRequestDto;
import com.msgrserver.model.dto.chat.DeleteUserByAdminRequestDto;
import com.msgrserver.model.dto.chat.DeleteUserByAdminResponseDto;

public interface ChatHandler {
    Response addUserByAdmin(AddUserByAdminRequestDto dto); //to be add to publicChatHandler

    Response deleteUserByAdmin(DeleteUserByAdminRequestDto dto);
}
