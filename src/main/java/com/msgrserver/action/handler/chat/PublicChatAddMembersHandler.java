package com.msgrserver.action.handler.chat;

import com.msgrserver.action.Action;
import com.msgrserver.action.ActionResult;
import com.msgrserver.action.ActionType;
import com.msgrserver.action.handler.ActionHandler;
import com.msgrserver.model.dto.chat.ChatDto;
import com.msgrserver.model.dto.chat.PublicChatAddMembersRequestDto;
import com.msgrserver.model.dto.chat.PublicChatAddMembersResponseDto;
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
public class PublicChatAddMembersHandler implements ActionHandler<PublicChatAddMembersRequestDto> {
    private final PublicChatService publicChatService;
    private final UserService userService;

    @Override
    public ActionType type() {
        return ActionType.ADD_NEW_MEMBERS;
    }

    @Override
    public ActionResult handle(Long userId, PublicChatAddMembersRequestDto dto) {

        PublicChat chat = publicChatService.addMembersToPublicChat(dto.getChatId(), userId, dto.getUserIds());

        Set<UserDto> users = publicChatService.usersCanBeAdd(dto.getChatId(), dto.getUserIds()).stream()
                .map(user -> Mapper.map(user, UserDto.class)).collect(Collectors.toSet());

        PublicChatAddMembersResponseDto responseDto = PublicChatAddMembersResponseDto.builder()
                .chat(Mapper.map(chat, ChatDto.class))
                .users(users)
                .adder(Mapper.map(userService.findUser(userId), UserDto.class))
                .build();

        Action action = Action.builder()
                .type(ActionType.ADD_NEW_MEMBERS)
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
