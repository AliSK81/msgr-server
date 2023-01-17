package com.msgrserver.handler.chat;

import com.msgrserver.action.Action;
import com.msgrserver.action.ActionResult;
import com.msgrserver.action.ActionType;
import com.msgrserver.model.dto.chat.ChatDeleteRequestDto;
import com.msgrserver.model.dto.chat.ChatDeleteResponseDto;
import com.msgrserver.model.dto.chat.ChatGetMessagesRequestDto;
import com.msgrserver.model.dto.chat.ChatGetMessagesResponseDto;
import com.msgrserver.model.dto.message.MessageDto;
import com.msgrserver.model.dto.user.UserDto;
import com.msgrserver.model.entity.chat.Chat;
import com.msgrserver.model.entity.chat.PublicChat;
import com.msgrserver.model.entity.user.User;
import com.msgrserver.repository.UserRepository;
import com.msgrserver.service.chat.ChatService;
import com.msgrserver.util.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class ChatHandlerImpl implements ChatHandler {

    private final ChatService chatService;
    private final UserRepository userRepository;

    @Override
    public ActionResult getChatMessages(Long userId, ChatGetMessagesRequestDto dto) {

        Set<MessageDto> messages = new HashSet<>();
        Set<UserDto> users = new HashSet<>();

        chatService.getChatMessages(dto.getChatId()).forEach(message -> {
            MessageDto messageDto = Mapper.map(message, MessageDto.class);
            messageDto.setSenderId(message.getSender().getId());
            messageDto.setChatId(message.getChat().getId());
            UserDto userDto = Mapper.map(message.getSender(), UserDto.class);
            messages.add(messageDto);
            users.add(userDto);
        });

        ChatGetMessagesResponseDto responseDto = ChatGetMessagesResponseDto.builder()
                .users(users)
                .messages(messages)
                .build();

        Action action = Action.builder()
                .type(ActionType.GET_CHAT_MESSAGES)
                .dto(responseDto)
                .build();

        return ActionResult.builder()
                .action(action)
                .receivers(Set.of(userId))
                .build();
    }

    @Override
    public ActionResult deleteChat(Long userId, ChatDeleteRequestDto dto) {
        Chat chat = chatService.findChat(dto.getChatId());
        Set<Long> receivers = new HashSet<>(userRepository.findMembersByChatId(dto.getChatId()).stream().map(User::getId).toList());
        if (chat instanceof PublicChat) {
            receivers.add(((PublicChat) chat).getOwner().getId());
            receivers.addAll(userRepository.findAdminsByChatId(dto.getChatId()).stream().map(User::getId).toList());
        }
        chatService.deleteChat(userId, dto.getChatId());
        ChatDeleteResponseDto responseDto = ChatDeleteResponseDto.builder()
                .chatId(dto.getChatId())
                .build();

        Action action = Action.builder()
                .type(ActionType.DELETE_CHAT)
                .dto(responseDto)
                .build();

        return ActionResult.builder()
                .action(action)
                .receivers(receivers)
                .build();
    }
}