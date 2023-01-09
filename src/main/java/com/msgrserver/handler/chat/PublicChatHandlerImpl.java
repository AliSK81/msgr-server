package com.msgrserver.handler.chat;

import com.msgrserver.action.Action;
import com.msgrserver.action.ActionType;
import com.msgrserver.action.ActionResult;
import com.msgrserver.model.dto.chat.*;
import com.msgrserver.model.entity.chat.PublicChat;
import com.msgrserver.model.entity.user.User;
import com.msgrserver.repository.PublicChatRepository;
import com.msgrserver.service.chat.PublicChatService;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class PublicChatHandlerImpl implements PublicChatHandler {
    PublicChatService publicChatService;
    PublicChatRepository publicChatRepository;

    @Override
    public ActionResult joinChatWithLink(PublicChatJoinWithLinkRequestDto dto) {
        PublicChat publicChat = publicChatRepository.findPublicChatByLink(dto.getLink());
        PublicChat chat = publicChatService.joinPublicChat(publicChat.getId(), dto.getUserId());
        PublicChatJoinWithLinkResponseDto responseDto = PublicChatJoinWithLinkResponseDto.builder()
                .chatId(chat.getId())
                .userId(dto.getUserId())
                .build();
        Action action = Action.builder()
                .type(ActionType.JOIN_CHAT_WITH_LINK)
                .dto(responseDto).build();
        Set<Long> receivers = new HashSet<>();//todo use function for get members id after merge
        return ActionResult.builder()
                .action(action)
                .receivers(receivers).build();
    }

    @Override
    public ActionResult leavePublicChat(PublicChatLeaveRequestDto dto) {
        PublicChat chat = publicChatService.leavePublicChat(dto.getChatId(), dto.getUserId());
        PublicChatLeaveResponseDto responseDto = PublicChatLeaveResponseDto.builder()
                .chatId(chat.getId())
                .userId(dto.getUserId())
                .build();
        Action action = Action.builder()
                .type(ActionType.LEAVE_PUBLIC_CHAT)
                .dto(responseDto).build();
        Set<Long> receivers = new HashSet<>();//todo use getResponse function after merge
        return ActionResult.builder()
                .action(action)
                .receivers(receivers).build();
    }

    @Override
    public ActionResult deletePublicChat(PublicChatDeleteRequestDto dto) {
        publicChatService.deletePublicChat(dto.getUserId() , dto.getChatId());
        PublicChatDeleteResponseDto responseDto=PublicChatDeleteResponseDto.builder()
                .chatId(dto.getChatId())
                .build();
        Action action = Action.builder()
                .type(ActionType.DELETE_PUBLIC_CHAT)
                .dto(responseDto)
                .build();
        Set<Long> receivers = new HashSet<>();//todo use getResponse function after merge
        return ActionResult.builder()
                .action(action)
                .receivers(receivers)
                .build();
    }

    @Override
    public ActionResult selectNewAdminPublicChat(PublicChatSelectNewAdminRequestDto dto) {
        PublicChat chat = publicChatService.selectNewAdminPublicChat(dto.getChatId(), dto.getSelectorId(), dto.getUserId());
        PublicChatSelectNewAdminResponseDto responseDto = PublicChatSelectNewAdminResponseDto.builder()
                .chatId(chat.getId())
                .userId(dto.getUserId())
                .build();
        Action action = Action.builder()
                .type(ActionType.SELECT_NEW_ADMIN_PUBLIC_CHAT)
                .dto(responseDto)
                .build();
        Set<Long> receivers = new HashSet<>();//todo use getResponse function after merge
        return ActionResult.builder()
                .action(action)
                .receivers(receivers)
                .build();
    }

    @Override
    public ActionResult deleteAdminPublicChat(PublicChatDeleteAdminRequestDto dto) {
        PublicChat chat = publicChatService.deleteAdminPublicChat(dto.getChatId(), dto.getSelectorId(), dto.getUserId());
        PublicChatDeleteAdminResponseDto responseDto = PublicChatDeleteAdminResponseDto.builder()
                .chatId(chat.getId())
                .userId(dto.getUserId())
                .build();
        Action action = Action.builder()
                .type(ActionType.DELETE_ADMIN_PUBLIC_CHAT)
                .dto(responseDto)
                .build();
        Set<Long> receivers = new HashSet<>();//todo use getResponse function after merge
        return ActionResult.builder()
                .action(action)
                .receivers(receivers)
                .build();
    }

}
