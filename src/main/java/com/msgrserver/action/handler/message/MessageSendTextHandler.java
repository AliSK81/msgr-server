package com.msgrserver.action.handler.message;

import com.msgrserver.action.Action;
import com.msgrserver.action.ActionResult;
import com.msgrserver.action.ActionType;
import com.msgrserver.action.handler.ActionHandler;
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
import com.msgrserver.service.chat.PrivateChatService;
import com.msgrserver.service.chat.PublicChatService;
import com.msgrserver.service.message.MessageService;
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
public class MessageSendTextHandler implements ActionHandler<MessageSendTextRequestDto> {
    private final MessageService messageService;
    private final PrivateChatService privateChatService;
    private final PublicChatService publicChatService;

    @Override
    public ActionType type() {
        return ActionType.SEND_TEXT;
    }

    @Override
    public ActionResult handle(Long userId, MessageSendTextRequestDto dto) {

        Long chatId;
        if (dto.isPrivate()) {

            long receiverId = dto.getToId();
            Optional<PrivateChat> chat = privateChatService.findPrivateChat(userId, receiverId);

            if (chat.isEmpty()) {
                chat = Optional.of(privateChatService.createPrivateChat(userId, receiverId));
            }

            chatId = chat.get().getId();

        } else {
            chatId = dto.getToId();
        }

        TextMessage newMessage = messageService.createText(
                chatId,
                userId,
                Mapper.map(dto, TextMessage.class)
        );

        Action action = getAction(newMessage);
        Set<Long> receivers = getReceivers(newMessage);

        return ActionResult.builder()
                .receivers(receivers)
                .action(action)
                .build();
    }

    private Action getAction(Message message) {

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

    private Set<Long> getReceivers(Message message) {
        Set<Long> receivers;

        if (message.getChat() instanceof PrivateChat chat) {
            receivers = new HashSet<>(List.of(chat.getUser1().getId(), chat.getUser2().getId()));

        } else if (message.getChat() instanceof PublicChat chat) {
            receivers = publicChatService.getChatMembers(chat.getId()).stream()
                    .map(User::getId)
                    .collect(Collectors.toSet());
        } else {
            throw new NotImplementedException();
        }

        return receivers;
    }
}
