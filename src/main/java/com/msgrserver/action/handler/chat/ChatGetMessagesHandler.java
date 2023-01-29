package com.msgrserver.action.handler.chat;

import com.msgrserver.action.Action;
import com.msgrserver.action.ActionResult;
import com.msgrserver.action.ActionType;
import com.msgrserver.action.handler.ActionHandler;
import com.msgrserver.model.dto.chat.ChatGetMessagesRequestDto;
import com.msgrserver.model.dto.chat.ChatGetMessagesResponseDto;
import com.msgrserver.model.dto.message.MessageDto;
import com.msgrserver.model.dto.user.UserDto;
import com.msgrserver.service.chat.ChatService;
import com.msgrserver.util.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class ChatGetMessagesHandler implements ActionHandler<ChatGetMessagesRequestDto> {
    private final ChatService chatService;

    @Override
    public ActionType type() {
        return ActionType.GET_CHAT_MESSAGES;
    }

    @Override
    public ActionResult handle(Long userId, ChatGetMessagesRequestDto dto) {

        Set<MessageDto> messages = new HashSet<>();
        Set<UserDto> users = new HashSet<>();

        chatService.getChatMessages(userId, dto.getChatId()).forEach(message -> {
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
}
