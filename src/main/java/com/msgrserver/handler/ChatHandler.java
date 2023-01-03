package com.msgrserver.handler;

import com.msgrserver.action.Response;
import com.msgrserver.model.dto.chat.AddUserByAdminRequestDto;
import com.msgrserver.model.dto.chat.DeleteUserByAdminRequestDto;

public interface ChatHandler {
    Response addUserByAdmin(AddUserByAdminRequestDto dto); //todo add to publicChatHandler

    Response deleteUserByAdmin(DeleteUserByAdminRequestDto dto);//todo be add to publicChatHandler
}
