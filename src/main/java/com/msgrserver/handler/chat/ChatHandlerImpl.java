package com.msgrserver.handler.chat;

import com.msgrserver.action.Action;
import com.msgrserver.action.ActionType;
import com.msgrserver.action.ActionResult;
import com.msgrserver.model.dto.chat.*;
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

    @Override
    public ActionResult getChatMessages(Long userId, ChatGetMessagesRequestDto dto) {
        return null;
    }
}