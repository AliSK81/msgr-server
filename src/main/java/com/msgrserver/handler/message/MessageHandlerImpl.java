package com.msgrserver.handler.message;

import com.msgrserver.action.Action;
import com.msgrserver.action.ActionResult;
import com.msgrserver.action.ActionType;
import com.msgrserver.exception.NotImplementedException;
import com.msgrserver.model.dto.chat.ChatDto;
import com.msgrserver.model.dto.message.MessageDto;
import com.msgrserver.model.dto.message.MessageSendTextRequestDto;
import com.msgrserver.model.dto.message.MessageSendTextResponseDto;
import com.msgrserver.model.dto.user.UserDto;
import com.msgrserver.model.entity.chat.Chat;
import com.msgrserver.model.entity.chat.PrivateChat;
import com.msgrserver.model.entity.chat.PublicChat;
import com.msgrserver.model.entity.message.Message;
import com.msgrserver.model.entity.message.TextMessage;
import com.msgrserver.model.entity.user.User;
import com.msgrserver.service.chat.ChatService;
import com.msgrserver.service.chat.PrivateChatService;
import com.msgrserver.service.chat.PublicChatService;
import com.msgrserver.service.message.MessageService;
import com.msgrserver.service.user.UserService;
import com.msgrserver.util.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MessageHandlerImpl implements MessageHandler {
    private final MessageService messageService;
    private final PrivateChatService privateChatService;
    private final PublicChatService publicChatService;
    private final UserService userService;
    private final ChatService chatService;



    public ActionResult sendText(Long senderId, MessageSendTextRequestDto dto) {

        Long chatId;
        if (dto.isPrivate()) {

            long receiverId = dto.getToId();
            Optional<PrivateChat> chat = privateChatService.findPrivateChat(senderId, receiverId);

            if (chat.isEmpty()) {
                chat = Optional.of(privateChatService.createPrivateChat(senderId, receiverId));
            }

            chatId = chat.get().getId();

        } else {
            chatId = dto.getToId();
        }

        TextMessage newMessage = messageService.createText(
                chatId,
                senderId,
                Mapper.map(dto, TextMessage.class)
        );

        Action action = getMessageReceiveAction(senderId, chatId, newMessage);
        Set<Long> receivers = getMessageReceivers(newMessage);

        return ActionResult.builder()
                .receivers(receivers)
                .action(action)
                .build();
    }

    private Action getMessageReceiveAction(Long senderId, Long chatId, Message message) {

        User sender = userService.findUser(senderId);
        Chat chat = chatService.findChat(chatId);

        ChatDto chatDto = Mapper.map(chat, ChatDto.class);
        MessageDto messageDto = Mapper.map(message, MessageDto.class);
        UserDto userDto = Mapper.map(sender, UserDto.class);

        messageDto.setSender(userDto);
        chatDto.setLastMessage(messageDto);

        MessageSendTextResponseDto newMessageDto = MessageSendTextResponseDto.builder()
                .chat(chatDto)
                .build();

        if(chat instanceof PrivateChat privateChat) {;
            chatDto.setOwnerId(privateChat.getParticipant(senderId).getId());
        }

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
            receivers = new HashSet<>(List.of(chat.getUser1().getId(), chat.getUser2().getId()));
        } else if (isPublic) {
            var chat = (PublicChat) message.getChat();
            receivers = chat.getMembers().stream()
                    .map(User::getId)
                    .collect(Collectors.toSet());
        } else {
            throw new NotImplementedException();
        }

        return receivers;
    }
}
