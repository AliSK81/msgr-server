package com.msgrserver.handler;

import com.msgrserver.action.Action;
import com.msgrserver.action.ActionType;
import com.msgrserver.action.Response;
import com.msgrserver.model.dto.message.MessageReceiveTextDto;
import com.msgrserver.model.dto.message.MessageSendTextDto;
import com.msgrserver.model.entity.message.TextMessage;
import com.msgrserver.model.entity.user.User;
import com.msgrserver.service.ChatService;
import com.msgrserver.service.MessageService;
import com.msgrserver.service.UserService;
import com.msgrserver.util.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MessageHandler {

    private final MessageService messageService;
    private final ChatService chatService;

    private final UserService userService;

    public Response sendText(MessageSendTextDto dto) {
        var newMessage = messageService.saveText(
                dto.getChatId(),
                Mapper.map(dto, TextMessage.class)
        );

        var newMessageDto = Mapper.map(newMessage, MessageReceiveTextDto.class);

        var action = Action.builder()
                .type(ActionType.SEND_TEXT)
                .dto(newMessageDto)
                .build();

        var chatId = dto.getChatId();

        Set<Long> receivers;

        if (chatId < 0) {
            chatId = -chatId;
            receivers = chatService.findChat(chatId).getMembers().stream()
                    .map(User::getId).collect(Collectors.toSet());
        } else {
            receivers = new HashSet<>(List.of(userService.findUser(chatId).getId()));
        }

        return Response.builder()
                .receivers(receivers)
                .action(action)
                .build();
    }
}
