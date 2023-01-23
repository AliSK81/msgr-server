package com.msgrserver.action.handler.chat;

import com.msgrserver.action.Action;
import com.msgrserver.action.ActionResult;
import com.msgrserver.action.ActionType;
import com.msgrserver.action.handler.ActionHandler;
import com.msgrserver.model.dto.chat.AdminDto;
import com.msgrserver.model.dto.chat.MemberDto;
import com.msgrserver.model.dto.chat.PublicChatGetMembersRequestDto;
import com.msgrserver.model.dto.chat.PublicChatGetMembersResponseDto;
import com.msgrserver.model.dto.user.UserDto;
import com.msgrserver.service.chat.PublicChatService;
import com.msgrserver.util.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PublicChatGetMembersHandler implements ActionHandler<PublicChatGetMembersRequestDto> {
    private final PublicChatService publicChatService;

    @Override
    public ActionType type() {
        return ActionType.GET_PUBLIC_CHAT_MEMBERS;
    }

    @Override
    public ActionResult handle(Long userId, PublicChatGetMembersRequestDto dto) {

        Set<UserDto> users = publicChatService.getChatMembers(dto.getChatId()).stream()
                .map(user -> Mapper.map(user, UserDto.class)).collect(Collectors.toSet());

        Set<MemberDto> members = users.stream()
                .map(user -> MemberDto.builder()
                        .chatId(dto.getChatId())
                        .userId(user.getId())
                        .build())
                .collect(Collectors.toSet());

        Set<AdminDto> admins = publicChatService.getChatAdmins(dto.getChatId()).stream()
                .map(user -> AdminDto.builder()
                        .chatId(dto.getChatId())
                        .userId(user.getId())
                        .build())
                .collect(Collectors.toSet());

        PublicChatGetMembersResponseDto responseDto = PublicChatGetMembersResponseDto.builder()
                .users(users)
                .members(members)
                .admins(admins)
                .build();

        Action action = Action.builder()
                .type(ActionType.GET_PUBLIC_CHAT_MEMBERS)
                .dto(responseDto)
                .build();

        return ActionResult.builder()
                .action(action)
                .receivers(Set.of(userId)).build();
    }
}
