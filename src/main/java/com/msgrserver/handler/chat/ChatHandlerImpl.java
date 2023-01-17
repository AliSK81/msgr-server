package com.msgrserver.handler.chat;

import com.msgrserver.action.Action;
import com.msgrserver.action.ActionResult;
import com.msgrserver.action.ActionType;
import com.msgrserver.model.dto.chat.ChatGetMessagesRequestDto;
import com.msgrserver.model.dto.chat.ChatGetMessagesResponseDto;
import com.msgrserver.model.dto.message.MessageDto;
import com.msgrserver.model.entity.message.Message;
import com.msgrserver.service.chat.ChatService;
import com.msgrserver.util.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ChatHandlerImpl implements ChatHandler {

    private final ChatService chatService;

    @Override
    public ActionResult getChatMessages(Long userId, ChatGetMessagesRequestDto dto) {

        Set<Message> messages = chatService.getChatMessages(dto.getChatId());

        ChatGetMessagesResponseDto responseDto = ChatGetMessagesResponseDto.builder()
                .chatId(dto.getChatId())
                .messages(messages.stream()
                .map(message -> Mapper.map(message, MessageDto.class))
                .collect(Collectors.toSet())).build();

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