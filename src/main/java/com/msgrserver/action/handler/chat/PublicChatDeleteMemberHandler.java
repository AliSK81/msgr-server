package com.msgrserver.action.handler.chat;

import com.msgrserver.action.Action;
import com.msgrserver.action.ActionResult;
import com.msgrserver.action.ActionType;
import com.msgrserver.action.handler.ActionHandler;
import com.msgrserver.model.dto.chat.PublicChatDeleteMemberRequestDto;
import com.msgrserver.model.dto.chat.PublicChatDeleteMemberResponseDto;
import com.msgrserver.model.dto.user.UserDto;
import com.msgrserver.model.entity.chat.PublicChat;
import com.msgrserver.model.entity.user.User;
import com.msgrserver.service.chat.PublicChatService;
import com.msgrserver.service.user.UserService;
import com.msgrserver.util.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PublicChatDeleteMemberHandler implements ActionHandler<PublicChatDeleteMemberRequestDto> {
    private final PublicChatService publicChatService;
    private final UserService userService;

    @Override
    public ActionType type() {
        return ActionType.DELETE_MEMBER;
    }

    @Override
    public ActionResult handle(Long deleterId, PublicChatDeleteMemberRequestDto dto) {

        Set<Long> receivers = publicChatService.getChatMembers(dto.getChatId()).stream()
                .map(User::getId).collect(Collectors.toSet());

        PublicChat chat = publicChatService.deleteMember(dto.getChatId(), deleterId, dto.getUserId());

        User user = userService.findUser(dto.getUserId());
        User deleter = userService.findUser(deleterId);

        PublicChatDeleteMemberResponseDto responseDto = PublicChatDeleteMemberResponseDto.builder()
                .chatId(chat.getId())
                .user(Mapper.map(user, UserDto.class))
                .deleter(Mapper.map(deleter, UserDto.class))
                .build();

        Action action = Action.builder()
                .type(ActionType.DELETE_MEMBER)
                .dto(responseDto).build();

        return ActionResult.builder()
                .action(action)
                .receivers(receivers)
                .build();
    }
}
