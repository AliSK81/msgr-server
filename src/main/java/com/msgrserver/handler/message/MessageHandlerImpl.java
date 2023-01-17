package com.msgrserver.handler.message;

import com.msgrserver.action.Action;
import com.msgrserver.action.ActionResult;
import com.msgrserver.action.ActionType;
import com.msgrserver.exception.NotImplementedException;
import com.msgrserver.model.dto.chat.ChatDto;
import com.msgrserver.model.dto.message.*;
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

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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

        Action action = getMessageReceiveAction(newMessage);
        Set<Long> receivers = getMessageReceivers(newMessage);

        return ActionResult.builder()
                .receivers(receivers)
                .action(action)
                .build();
    }

    private Action getMessageReceiveAction(Message message) {

        User sender = message.getSender();
        Chat chat = message.getChat();

        ChatDto chatDto = Mapper.map(chat, ChatDto.class);
        UserDto userDto = Mapper.map(sender, UserDto.class);
        MessageDto messageDto = Mapper.map(message, MessageDto.class);

        messageDto.setSenderId(sender.getId());
        messageDto.setChatId(chat.getId());

        if (chat instanceof PrivateChat privateChat) {
            User receiver = privateChat.getParticipant(sender.getId());
            chatDto.setUser1Id(sender.getId());
            chatDto.setUser2Id(receiver.getId());
        }

        MessageSendTextResponseDto newMessageDto = MessageSendTextResponseDto.builder()
                .chat(chatDto)
                .user(userDto)
                .message(messageDto)
                .build();

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
            receivers = publicChatService.getChatMembers(chat.getId()).stream()
                    .map(User::getId)
                    .collect(Collectors.toSet());
        } else {
            throw new NotImplementedException();
        }

        return receivers;
    }

    @Override
    public ActionResult deleteMessage(Long userId, MessageDeleteRequestDto dto) {
        Message message = messageService.findMessage(dto.getMessageId());
        Set<Long> receivers = getMessageReceivers(message);

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
}
