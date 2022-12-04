package com.msgrserver.handler;

import com.msgrserver.action.Action;
import com.msgrserver.action.ActionType;
import com.msgrserver.action.Response;
import com.msgrserver.model.dto.message.MessageReceiveTextDto;
import com.msgrserver.model.entity.message.TextMessage;
import com.msgrserver.model.entity.user.User;
import com.msgrserver.model.dto.message.MessageSendTextDto;
import com.msgrserver.service.ChatService;
import com.msgrserver.util.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ChatHandler {
    private final ChatService chatService;



}
