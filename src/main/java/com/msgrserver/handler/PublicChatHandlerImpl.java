package com.msgrserver.handler;

import com.msgrserver.action.Action;
import com.msgrserver.action.ActionType;
import com.msgrserver.action.Response;
import com.msgrserver.model.dto.chat.ChatJoinWithLinkRequestDto;
import com.msgrserver.model.dto.chat.ChatJoinWithLinkResponseDto;
import com.msgrserver.model.entity.chat.PublicChat;
import com.msgrserver.repository.PublicChatRepository;
import com.msgrserver.service.PublicChatService;

import java.util.HashSet;
import java.util.Set;

public class PublicChatHandlerImpl implements PublicChatHandler {
    PublicChatService publicChatService;
    PublicChatRepository publicChatRepository;
    @Override
    public Response joinChatWithLink(ChatJoinWithLinkRequestDto dto) {
        PublicChat publicChat = publicChatRepository.findPublicChatByLink(dto.getLink());
        PublicChat chat = publicChatService.joinPublicChat( publicChat.getId(), dto.getUserId());
        ChatJoinWithLinkResponseDto responseDto = ChatJoinWithLinkResponseDto.builder()
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
