package com.msgrserver.action.handler.chat;

import com.msgrserver.action.Action;
import com.msgrserver.action.ActionResult;
import com.msgrserver.action.ActionType;
import com.msgrserver.action.handler.ActionHandler;
import com.msgrserver.model.dto.chat.PrivateChatDeleteRequestDto;
import com.msgrserver.model.dto.chat.PrivateChatDeleteResponseDto;
import com.msgrserver.model.entity.chat.PrivateChat;
import com.msgrserver.service.chat.ChatService;
import com.msgrserver.service.chat.PrivateChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class PrivateChatDeleteHandler implements ActionHandler<PrivateChatDeleteRequestDto> {
    private final PrivateChatService privateChatService;
    private final ChatService chatService;

    @Override
    public ActionType type() {
        return ActionType.DELETE_PRIVATE_CHAT;
    }

    @Override
    public ActionResult handle(Long userId, PrivateChatDeleteRequestDto dto) {

        PrivateChat chat = privateChatService.findPrivateChat(dto.getChatId());

        chatService.deleteChat(userId, dto.getChatId());

        Set<Long> receivers = new HashSet<>(List.of(chat.getUser1().getId(), chat.getUser2().getId()));

        PrivateChatDeleteResponseDto responseDto = PrivateChatDeleteResponseDto.builder()
                .chatId(dto.getChatId())
                .build();

        Action action = Action.builder()
                .type(ActionType.DELETE_PRIVATE_CHAT)
                .dto(responseDto)
                .build();

        return ActionResult.builder()
                .action(action)
                .receivers(receivers)
                .build();
    }
}
