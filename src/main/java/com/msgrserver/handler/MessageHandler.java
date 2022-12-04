package com.msgrserver.handler;

import com.msgrserver.action.Action;
import com.msgrserver.action.ActionType;
import com.msgrserver.action.Response;
import com.msgrserver.model.dto.message.MessageReceiveTextDto;
import com.msgrserver.model.dto.message.MessageSendTextDto;
import com.msgrserver.model.entity.message.TextMessage;
import com.msgrserver.model.entity.user.User;
import com.msgrserver.service.MessageService;
import com.msgrserver.util.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MessageHandler {

    private final MessageService messageService;

    public Response sendText(MessageSendTextDto dto) {
        var newMessage = messageService.sendText(
                dto.getChatId(),
                Mapper.map(dto, TextMessage.class)
        );

        var newMessageDto = Mapper.map(newMessage, MessageReceiveTextDto.class);

        newMessageDto.setChatId(newMessage.getChat().getId());

        var action = Action.builder()
                .type(ActionType.SEND_TEXT)
                .dto(newMessageDto)
                .build();

        return Response.builder()
                .receivers(newMessage.getChat().getUsers().stream().map(User::getId).collect(Collectors.toSet()))
                .action(action)
                .build();
    }
}
