package com.msgrserver.action.handler.chat;

import com.msgrserver.action.Action;
import com.msgrserver.action.ActionResult;
import com.msgrserver.action.ActionType;
import com.msgrserver.action.handler.ActionHandler;
import com.msgrserver.model.dto.chat.PublicChatLeaveRequestDto;
import com.msgrserver.model.dto.chat.PublicChatLeaveResponseDto;
import com.msgrserver.model.dto.user.UserDto;
import com.msgrserver.model.entity.chat.PublicChat;
import com.msgrserver.model.entity.user.User;
import com.msgrserver.service.chat.ChatService;
import com.msgrserver.service.chat.PublicChatService;
import com.msgrserver.service.user.UserService;
import com.msgrserver.util.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PublicChatLeaveHandler implements ActionHandler<PublicChatLeaveRequestDto> {
    private final PublicChatService publicChatService;
    private final UserService userService;
    private final ChatService chatService;

    @Override
    public ActionType type() {
        return ActionType.LEAVE_PUBLIC_CHAT;
    }

    @Override
    public ActionResult handle(Long userId, PublicChatLeaveRequestDto dto) {
        Set<Long> receivers = publicChatService.getChatMembers(dto.getChatId()).stream()
                .map(User::getId).collect(Collectors.toSet());

        PublicChat chat = publicChatService.findPublicChat(dto.getChatId());

        if (chat.getOwner().getId().equals(userId)) {
            chatService.deleteChat(userId, dto.getChatId());
        } else {
            publicChatService.leavePublicChat(dto.getChatId(), userId);
        }

        PublicChatLeaveResponseDto responseDto = PublicChatLeaveResponseDto.builder()
                .chatId(chat.getId())
                .user(Mapper.map(userService.findUser(userId), UserDto.class))
                .build();

        Action action = Action.builder()
                .type(ActionType.LEAVE_PUBLIC_CHAT)
                .dto(responseDto).build();

        return ActionResult.builder()
                .action(action)
                .receivers(receivers)
                .build();
    }
}
