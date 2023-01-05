package com.msgrserver.handler;

import com.msgrserver.action.Action;
import com.msgrserver.action.ActionType;
import com.msgrserver.action.Response;
import com.msgrserver.model.dto.chat.AddUserByAdminRequestDto;
import com.msgrserver.model.dto.chat.AddUserByAdminResponseDto;
import com.msgrserver.model.dto.chat.DeleteUserByAdminRequestDto;
import com.msgrserver.model.dto.chat.DeleteUserByAdminResponseDto;
import com.msgrserver.model.entity.chat.PublicChat;
import com.msgrserver.model.entity.user.User;
import com.msgrserver.service.PublicChatService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class ChatHandlerImpl implements ChatHandler {

    PublicChatService publicChatService;
    PublicChat publicChat;

    @SneakyThrows
    @Override
    public Response addUserByAdmin(AddUserByAdminRequestDto dto) {
        PublicChat chat = publicChatService.addUserToPublicChat(dto.getChatId(), dto.getAdminId(), dto.getUserId());

        AddUserByAdminResponseDto responseDto = AddUserByAdminResponseDto.builder()
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
    public Response deleteUserByAdmin(DeleteUserByAdminRequestDto dto) {
        PublicChat chat = publicChatService.deleteUserFromPublicChat(dto.getChatId(), dto.getAdminId(), dto.getUserId());
        DeleteUserByAdminResponseDto responseDto = DeleteUserByAdminResponseDto.builder()
                .chatId(chat.getId())
                .userId(dto.getUserId())
                .adminId(dto.getAdminId())
                .build();
        Action action = Action.builder()
                .type(ActionType.DELETE_USER_BY_ADMIN)
                .dto(responseDto).build();

        return getResponse(chat, action);
    }

    private Response getResponse(PublicChat chat, Action action) {
        Set<Long> receivers = new HashSet<>();
        switch (chat.getType()) {
            case GROUP -> receivers.addAll(chat.getUsers().stream().map(User::getId).toList());
            case CHANNEL -> {
                receivers.add(chat.getOwner().getId());
                receivers.addAll(chat.getAdmins().stream().map(User::getId).toList());
            }
        }
        return Response.builder()
                .action(action)
                .receivers(receivers).build();
    }
}