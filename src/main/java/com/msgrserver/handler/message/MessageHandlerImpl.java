package com.msgrserver.handler.message;

import com.msgrserver.action.Action;
import com.msgrserver.action.ActionResult;
import com.msgrserver.action.ActionType;
import com.msgrserver.exception.BadRequestException;
import com.msgrserver.exception.ChatNotFoundException;
import com.msgrserver.exception.NotImplementedException;
import com.msgrserver.model.dto.message.MessageReceiveTextDto;
import com.msgrserver.model.dto.message.MessageSendTextDto;
import com.msgrserver.model.entity.chat.Member;
import com.msgrserver.model.entity.chat.MemberId;
import com.msgrserver.model.entity.chat.PrivateChat;
import com.msgrserver.model.entity.chat.PublicChat;
import com.msgrserver.model.entity.message.Message;
import com.msgrserver.model.entity.message.TextMessage;
import com.msgrserver.service.chat.PrivateChatService;
import com.msgrserver.service.message.MessageService;
import com.msgrserver.util.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MessageHandlerImpl implements MessageHandler {
    private final MessageService messageService;
    private final PrivateChatService privateChatService;

    public ActionResult sendText(Long senderId, MessageSendTextDto dto) {

        Long chatId = dto.getChatId();
        Long receiverId = dto.getReceiverId();

        if (receiverId != null) {
            if (chatId != null)
                throw new BadRequestException();

            PrivateChat chat;
            try {
                chat = privateChatService.findPrivateChat(senderId, receiverId);
            } catch (ChatNotFoundException ex) {
                chat = privateChatService.createPrivateChat(senderId, receiverId);
            }
            chatId = chat.getId();
        }

        TextMessage newMessage = messageService.createText(
                chatId,
                senderId,
                Mapper.map(dto, TextMessage.class)
        );

        Action action = getMessageReceiveAction(newMessage);
        Set<Long> receivers = getMessageReceivers(newMessage);

        return ActionResult.builder()
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
        boolean isPublic = message.getChat() instanceof PublicChat;

        if (isPrivate) {
            var chat = (PrivateChat) message.getChat();
            receivers = Set.of(chat.getUser1().getId(), chat.getUser2().getId());

        } else if (isPublic) {
            var chat = (PublicChat) message.getChat();
            receivers = chat.getMembers().stream()
                    .map(Member::getId)
                    .map(MemberId::getUserId)
                    .collect(Collectors.toSet());
        } else {
            throw new NotImplementedException();
        }

        return receivers;
    }
}
