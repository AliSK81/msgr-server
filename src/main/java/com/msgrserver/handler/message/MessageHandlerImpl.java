package com.msgrserver.handler.message;

import com.msgrserver.action.Action;
import com.msgrserver.action.ActionType;
import com.msgrserver.action.ActionResult;
import com.msgrserver.exception.NotImplementedException;
import com.msgrserver.model.dto.message.MessageDeleteRequestDto;
import com.msgrserver.model.dto.message.MessageDeleteResponseDto;
import com.msgrserver.model.dto.message.MessageReceiveTextDto;
import com.msgrserver.model.dto.message.MessageSendTextDto;
import com.msgrserver.model.entity.chat.PrivateChat;
import com.msgrserver.model.entity.chat.PublicChat;
import com.msgrserver.model.entity.message.Message;
import com.msgrserver.model.entity.message.TextMessage;
import com.msgrserver.model.entity.user.User;
import com.msgrserver.service.message.MessageService;
import com.msgrserver.util.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MessageHandlerImpl implements MessageHandler {
    private final MessageService messageService;


    public ActionResult sendText(MessageSendTextDto dto) {
        TextMessage newMessage = messageService.saveText(
                dto.getChatId(),
                dto.getSenderId(),
                Mapper.map(dto, TextMessage.class)
        );

        Action action = getMessageReceiveAction(newMessage);
        Set<Long> receivers = getMessageReceivers(newMessage);

        return ActionResult.builder()
                .receivers(receivers)
                .action(action)
                .build();
    }

    @Override
    public ActionResult deleteMessage(Long userId, MessageDeleteRequestDto dto) {
        Message message = messageService.findMessage(dto.getMessageId());
        Set<Long> receivers = getMessageReceivers(message);

        Long chatId = messageService.deleteMessage(userId,dto.getMessageId());

        MessageDeleteResponseDto responseDto = MessageDeleteResponseDto.builder()
                .messageId(dto.getMessageId())
                .chatId(chatId).build();

        Action action = Action.builder()
                .dto(responseDto)
                .type(ActionType.DELETE_MESSAGE)
                .build();

        return ActionResult.builder()
                .action(action)
                .receivers(receivers)
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
        boolean isPublic = message.getChat() instanceof PublicChat;

        if (isPrivate) {
            var chat = (PrivateChat) message.getChat();
            User participant = chat.getParticipant(message.getSender().getId());
            receivers = new HashSet<>(List.of(participant.getId()));
        } else if (isPublic) {
            var chat = (PublicChat) message.getChat();
            receivers = chat.getUsers().stream()
                    .map(User::getId)
                    .collect(Collectors.toSet());
        } else {
            throw new NotImplementedException();
        }

        return receivers;
    }
}
