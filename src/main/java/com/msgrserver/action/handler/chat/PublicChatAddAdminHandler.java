package com.msgrserver.action.handler.chat;

import com.msgrserver.action.Action;
import com.msgrserver.action.ActionResult;
import com.msgrserver.action.ActionType;
import com.msgrserver.action.handler.ActionHandler;
import com.msgrserver.model.dto.chat.PublicChatSelectNewAdminRequestDto;
import com.msgrserver.model.dto.chat.PublicChatSelectNewAdminResponseDto;
import com.msgrserver.model.entity.chat.PublicChat;
import com.msgrserver.model.entity.user.User;
import com.msgrserver.service.chat.PublicChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PublicChatAddAdminHandler implements ActionHandler<PublicChatSelectNewAdminRequestDto> {
    private final PublicChatService publicChatService;

    @Override
    public ActionType type() {
        return ActionType.SELECT_NEW_ADMIN_PUBLIC_CHAT;
    }

    @Override
    public ActionResult handle(Long userId, PublicChatSelectNewAdminRequestDto dto) {

        PublicChat chat = publicChatService.selectNewAdminPublicChat(dto.getChatId(), userId, dto.getUserId());

        PublicChatSelectNewAdminResponseDto responseDto = PublicChatSelectNewAdminResponseDto.builder()
                .chatId(chat.getId())
                .userId(dto.getUserId())
                .build();

        Action action = Action.builder()
                .type(ActionType.SELECT_NEW_ADMIN_PUBLIC_CHAT)
                .dto(responseDto)
                .build();

        Set<Long> receivers = publicChatService.getChatMembers(dto.getChatId()).stream()
                .map(User::getId).collect(Collectors.toSet());

        return ActionResult.builder()
                .action(action)
                .receivers(receivers)
                .build();
    }
}
