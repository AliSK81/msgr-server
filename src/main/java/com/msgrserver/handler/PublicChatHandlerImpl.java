package com.msgrserver.handler;

import com.msgrserver.action.Action;
import com.msgrserver.action.ActionType;
import com.msgrserver.action.Response;
import com.msgrserver.model.dto.chat.ChatRequestJoinChatWithLinkDto;
import com.msgrserver.model.dto.chat.ChatResponseJoinChatWithLinkDto;
import com.msgrserver.model.entity.chat.PublicChat;
import com.msgrserver.service.PublicChatServiceImpl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PublicChatHandlerImpl implements PublicChatHandler {
    PublicChatServiceImpl publicChatService;

    @Override
    public Response joinChatWithLink(ChatRequestJoinChatWithLinkDto dto) {
        PublicChat chat = publicChatService.joinChatWithLink(dto.getLink(), dto.getUserId());
        ChatResponseJoinChatWithLinkDto responseDto = ChatResponseJoinChatWithLinkDto.builder()
                .chatId(chat.getId())
                .userId(dto.getUserId())
                .build();
        Action action = Action.builder()
                .type(ActionType.JOIN_CHAT_WITH_LINK)
                .dto(responseDto).build();
        Set<Long> receivers = new HashSet<>();//todo use function for get members id after merge
        return Response.builder()
                .action(action)
                .receivers(receivers).build();
    }
}
