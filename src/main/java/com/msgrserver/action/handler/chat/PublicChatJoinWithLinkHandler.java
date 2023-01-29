package com.msgrserver.action.handler.chat;

import com.msgrserver.action.Action;
import com.msgrserver.action.ActionResult;
import com.msgrserver.action.ActionType;
import com.msgrserver.action.handler.ActionHandler;
import com.msgrserver.model.dto.chat.PublicChatJoinWithLinkRequestDto;
import com.msgrserver.model.dto.chat.PublicChatJoinWithLinkResponseDto;
import com.msgrserver.model.entity.chat.PublicChat;
import com.msgrserver.model.entity.user.User;
import com.msgrserver.repository.PublicChatRepository;
import com.msgrserver.service.chat.PublicChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PublicChatJoinWithLinkHandler implements ActionHandler<PublicChatJoinWithLinkRequestDto> {
    private final PublicChatService publicChatService;
    private final PublicChatRepository publicChatRepository;

    @Override
    public ActionType type() {
        return ActionType.JOIN_CHAT_WITH_LINK;
    }

    @Override
    public ActionResult handle(Long userId, PublicChatJoinWithLinkRequestDto dto) {

        PublicChat publicChat = publicChatRepository.findPublicChatByLink(dto.getLink());
        PublicChat chat = publicChatService.joinPublicChat(publicChat.getId(), userId);

        PublicChatJoinWithLinkResponseDto responseDto = PublicChatJoinWithLinkResponseDto.builder()
                .chatId(chat.getId())
                .userId(userId)
                .build();

        Action action = Action.builder()
                .type(ActionType.JOIN_CHAT_WITH_LINK)
                .dto(responseDto).build();

        Set<Long> receivers = publicChatService.getChatMembers(chat.getId()).stream()
                .map(User::getId).collect(Collectors.toSet());

        return ActionResult.builder()
                .action(action)
                .receivers(receivers)
                .build();
    }

}
