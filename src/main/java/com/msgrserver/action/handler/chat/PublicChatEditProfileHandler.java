package com.msgrserver.action.handler.chat;

import com.msgrserver.action.Action;
import com.msgrserver.action.ActionResult;
import com.msgrserver.action.ActionType;
import com.msgrserver.action.handler.ActionHandler;
import com.msgrserver.model.dto.chat.ChatDto;
import com.msgrserver.model.dto.chat.PublicChatEditProfileRequestDto;
import com.msgrserver.model.dto.chat.PublicChatEditProfileResponseDto;
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
public class PublicChatEditProfileHandler implements ActionHandler<PublicChatEditProfileRequestDto> {
    private final PublicChatService publicChatService;

    @Override
    public ActionType type() {
        return ActionType.EDIT_PROFILE_PUBLIC_CHAT;
    }

    @Override
    public ActionResult handle(Long userId, PublicChatEditProfileRequestDto dto) {

        PublicChat chat = Mapper.map(dto.getChat(), PublicChat.class);

        PublicChat editedChat = publicChatService.editProfilePublicChat(chat, userId);

        PublicChatEditProfileResponseDto responseDto = PublicChatEditProfileResponseDto.builder()
                .chat(Mapper.map(editedChat, ChatDto.class))
                .build();

        Action action = Action.builder()
                .type(ActionType.EDIT_PROFILE_PUBLIC_CHAT)
                .dto(responseDto).build();

        Set<Long> receivers = publicChatService.getChatMembers(editedChat.getId()).stream()
                .map(User::getId)
                .collect(Collectors.toSet());

        return ActionResult.builder()
                .action(action)
                .receivers(receivers).build();
    }

}
