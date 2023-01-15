package com.msgrserver.handler.chat;

import com.msgrserver.action.Action;
import com.msgrserver.action.ActionType;
import com.msgrserver.action.ActionResult;
import com.msgrserver.model.dto.chat.PublicChatAddUserRequestDto;
import com.msgrserver.model.dto.chat.PublicChatAddUserResponseDto;
import com.msgrserver.model.dto.chat.PublicChatDeleteUserRequestDto;
import com.msgrserver.model.dto.chat.PublicChatDeleteUserResponseDto;
import com.msgrserver.model.entity.chat.PublicChat;
import com.msgrserver.model.entity.user.User;
import com.msgrserver.service.chat.PublicChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class ChatHandlerImpl implements ChatHandler {

}