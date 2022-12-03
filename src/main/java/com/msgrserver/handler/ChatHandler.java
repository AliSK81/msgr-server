package com.msgrserver.handler;

import com.msgrserver.action.Action;
import com.msgrserver.action.ActionType;
import com.msgrserver.action.Response;
import com.msgrserver.model.dto.message.MessageReceiveTextDto;
import com.msgrserver.model.entity.message.TextMessage;
import com.msgrserver.model.entity.user.User;
import com.msgrserver.model.dto.message.MessageSendTextDto;
import com.msgrserver.service.ChatService;
import com.msgrserver.util.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ChatHandler {
    private final ChatService chatService;

    public Response sendText(Long userId, MessageSendTextDto dto) {
        var chat = chatService.sendText(
                dto.getChatId(),
                userId,
                Mapper.map(dto, TextMessage.class)
        );

        var newMessage = (TextMessage) chat.getMessages().last();

        var newMessageDto = Mapper.map(newMessage, MessageReceiveTextDto.class);
        newMessageDto.setChatId(chat.getId());

        var action = Action.builder()
                .type(ActionType.SEND_TEXT)
                .dto(newMessageDto)
                .build();

        return Response.builder()
                .receivers(chat.getUsers().stream().map(User::getId).collect(Collectors.toSet()))
                .action(action)
                .build();
    }

}
