package com.msgrserver.handler;

import com.msgrserver.action.Action;
import com.msgrserver.action.ActionType;
import com.msgrserver.action.Response;
import com.msgrserver.model.dto.message.MessageReceiveTextDto;
import com.msgrserver.model.dto.message.MessageSendTextDto;
import com.msgrserver.model.entity.chat.PrivateChat;
import com.msgrserver.model.entity.chat.PublicChat;
import com.msgrserver.model.entity.message.Message;
import com.msgrserver.model.entity.message.TextMessage;
import com.msgrserver.model.entity.user.User;
import com.msgrserver.service.MessageService;
import com.msgrserver.util.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MessageHandlerImpl {
    private final MessageService messageService;

    public Response sendText(MessageSendTextDto dto) {
        TextMessage newMessage = messageService.saveText(
                dto.getChatId(),
                Mapper.map(dto, TextMessage.class)
        );

        Action action = getMessageReceiveAction(newMessage);
        Set<Long> receivers = getMessageReceivers(newMessage);

        return Response.builder()
                .receivers(receivers)
                .action(action)
                .build();
    }

    private Action getMessageReceiveAction(Message message) {
        MessageReceiveTextDto newMessageDto = Mapper.map(message, MessageReceiveTextDto.class);

        return Action.builder()
                .type(ActionType.SEND_TEXT)
                .dto(newMessageDto)
                .build();
    }

    private Set<Long> getMessageReceivers(Message message) {
        Set<Long> receivers;

        boolean isPrivate = message.getChat() instanceof PrivateChat;
        if (isPrivate) {
            var chat = (PrivateChat) message.getChat();
            receivers = new HashSet<>(Collections.singleton(
                    chat.getReceiverId(message.getSenderId())));
        } else {
            var chat = (PublicChat) message.getChat();
            receivers = chat.getMembers().stream()
                    .map(User::getId)
                    .collect(Collectors.toSet());
        }

        return receivers;
    }
}