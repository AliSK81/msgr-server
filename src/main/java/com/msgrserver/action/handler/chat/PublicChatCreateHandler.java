package com.msgrserver.action.handler.chat;

import com.msgrserver.action.Action;
import com.msgrserver.action.ActionResult;
import com.msgrserver.action.ActionType;
import com.msgrserver.action.handler.ActionHandler;
import com.msgrserver.model.dto.chat.ChatDto;
import com.msgrserver.model.dto.chat.PublicChatCreateRequestDto;
import com.msgrserver.model.dto.chat.PublicChatCreateResponseDto;
import com.msgrserver.model.entity.chat.PublicChat;
import com.msgrserver.model.entity.user.User;
import com.msgrserver.service.chat.PublicChatService;
import com.msgrserver.util.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PublicChatCreateHandler implements ActionHandler<PublicChatCreateRequestDto> {
    private final PublicChatService publicChatService;

    @Override
    public ActionType type() {
        return ActionType.CREATE_PUBLIC_CHAT;
    }

    @Override
    public ActionResult handle(Long userId, PublicChatCreateRequestDto dto) {
        PublicChat chatSend = Mapper.map(dto.getChat(), PublicChat.class);

        PublicChat chat = publicChatService.createPublicChat(userId, chatSend, dto.getInitMemberIds());

        PublicChatCreateResponseDto responseDto = PublicChatCreateResponseDto.builder()
                .chat(Mapper.map(chat, ChatDto.class))
                .build();

        Action action = Action.builder()
                .type(ActionType.CREATE_PUBLIC_CHAT)
                .dto(responseDto).build();

        Set<Long> receivers = publicChatService.getChatMembers(chat.getId()).stream()
                .map(User::getId).collect(Collectors.toSet());

        return ActionResult.builder()
                .action(action)
                .receivers(receivers).build();
    }
}
