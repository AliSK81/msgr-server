package com.msgrserver.handler.chat;

import com.msgrserver.action.Action;
import com.msgrserver.action.ActionResult;
import com.msgrserver.action.ActionType;
import com.msgrserver.model.dto.chat.request.PublicChatAddUserRequestDto;
import com.msgrserver.model.dto.chat.request.PublicChatDeleteUserRequestDto;
import com.msgrserver.model.dto.chat.response.PublicChatAddUserResponseDto;
import com.msgrserver.model.dto.chat.response.PublicChatDeleteUserResponseDto;
import com.msgrserver.model.entity.chat.PublicChat;
import com.msgrserver.model.entity.user.User;
import com.msgrserver.service.chat.PublicChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class ChatHandlerImpl implements ChatHandler {

    private final PublicChatService publicChatService;

    @Override
    public ActionResult addUserToPublicChat(PublicChatAddUserRequestDto dto) {
        PublicChat chat = publicChatService.addUserToPublicChat(dto.getChatId(), dto.getAdminId(), dto.getUserId());

        PublicChatAddUserResponseDto responseDto = PublicChatAddUserResponseDto.builder()
                .chatId(chat.getId())
                .userId(dto.getUserId())
                .adminId(dto.getAdminId())
                .build();
        Action action = Action.builder()
                .type(ActionType.ADD_USER_BY_ADMIN)
                .dto(responseDto).build();
        return getResponse(chat, action);
    }

    @Override
    public ActionResult deleteUserFromPublicChat(PublicChatDeleteUserRequestDto dto) {
        PublicChat chat = publicChatService.deleteUserFromPublicChat(dto.getChatId(), dto.getAdminId(), dto.getUserId());
        PublicChatDeleteUserResponseDto responseDto = PublicChatDeleteUserResponseDto.builder()
                .chatId(chat.getId())
                .userId(dto.getUserId())
                .adminId(dto.getAdminId())
                .build();
        Action action = Action.builder()
                .type(ActionType.DELETE_USER_BY_ADMIN)
                .dto(responseDto).build();

        return getResponse(chat, action);
    }

    private ActionResult getResponse(PublicChat chat, Action action) {
        Set<Long> receivers = new HashSet<>();
        switch (chat.getType()) {
            case GROUP -> receivers.addAll(chat.getUsers().stream().map(User::getId).toList());
            case CHANNEL -> {
                receivers.add(chat.getOwner().getId());
                receivers.addAll(chat.getAdmins().stream().map(User::getId).toList());
            }
        }
        return ActionResult.builder()
                .action(action)
                .receivers(receivers).build();
    }
}