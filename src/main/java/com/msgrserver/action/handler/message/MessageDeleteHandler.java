package com.msgrserver.action.handler.message;

import com.msgrserver.action.Action;
import com.msgrserver.action.ActionResult;
import com.msgrserver.action.ActionType;
import com.msgrserver.action.handler.ActionHandler;
import com.msgrserver.exception.NotImplementedException;
import com.msgrserver.model.dto.message.MessageDeleteRequestDto;
import com.msgrserver.model.dto.message.MessageDeleteResponseDto;
import com.msgrserver.model.entity.chat.PrivateChat;
import com.msgrserver.model.entity.chat.PublicChat;
import com.msgrserver.model.entity.message.Message;
import com.msgrserver.model.entity.user.User;
import com.msgrserver.service.chat.PublicChatService;
import com.msgrserver.service.message.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MessageDeleteHandler implements ActionHandler<MessageDeleteRequestDto> {
    private final MessageService messageService;
    private final PublicChatService publicChatService;

    @Override
    public ActionType type() {
        return ActionType.DELETE_MESSAGE;
    }

    @Override
    public ActionResult handle(Long userId, MessageDeleteRequestDto dto) {

        Message message = messageService.findMessage(dto.getMessageId());
        Set<Long> receivers = getReceivers(message);

        Long chatId = messageService.deleteMessage(userId, dto.getMessageId());

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

    private Set<Long> getReceivers(Message message) {
        Set<Long> receivers;

        boolean isPrivate = message.getChat() instanceof PrivateChat;
        boolean isPublic = message.getChat() instanceof PublicChat;

        if (isPrivate) {
            var chat = (PrivateChat) message.getChat();
            receivers = new HashSet<>(List.of(chat.getUser1().getId(), chat.getUser2().getId()));
        } else if (isPublic) {
            var chat = (PublicChat) message.getChat();
            receivers = publicChatService.getChatMembers(chat.getId()).stream()
                    .map(User::getId)
                    .collect(Collectors.toSet());
        } else {
            throw new NotImplementedException();
        }

        return receivers;
    }
}
