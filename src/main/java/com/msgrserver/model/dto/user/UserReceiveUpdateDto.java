package com.msgrserver.model.dto.user;

import com.msgrserver.model.dto.ActionDto;
import com.msgrserver.model.dto.chat.ChatReceiveProfileUpdateDto;

import java.util.Set;

public class UserReceiveUpdateDto extends ActionDto {

    Set<ChatReceiveProfileUpdateDto> chat;
}
