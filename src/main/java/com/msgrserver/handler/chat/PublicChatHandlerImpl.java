package com.msgrserver.handler.chat;

import com.msgrserver.action.Action;
import com.msgrserver.action.ActionResult;
import com.msgrserver.action.ActionType;
import com.msgrserver.exception.UserNotFoundException;
import com.msgrserver.model.dto.chat.*;
import com.msgrserver.model.dto.user.UserDto;
import com.msgrserver.model.entity.chat.PublicChat;
import com.msgrserver.model.entity.user.User;
import com.msgrserver.repository.PublicChatRepository;
import com.msgrserver.repository.UserRepository;
import com.msgrserver.service.chat.PublicChatService;
import com.msgrserver.util.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PublicChatHandlerImpl implements PublicChatHandler {
    private final PublicChatService publicChatService;
    private final PublicChatRepository publicChatRepository;
    private final UserRepository userRepository;

    @Override
    public ActionResult joinChatWithLink(Long userId, PublicChatJoinWithLinkRequestDto dto) {
        PublicChat publicChat = publicChatRepository.findPublicChatByLink(dto.getLink());
        PublicChat chat = publicChatService.joinPublicChat(publicChat.getId(), userId);

        PublicChatJoinWithLinkResponseDto responseDto = PublicChatJoinWithLinkResponseDto.builder()
                .chatId(chat.getId())
                .userId(userId)
                .build();
        Action action = Action.builder()
                .type(ActionType.JOIN_CHAT_WITH_LINK)
                .dto(responseDto).build();
        return getResponse(chat, action);
    }

    @Override
    public ActionResult addMembersToPublicChat(Long adminId, PublicChatAddMembersRequestDto dto) {
        PublicChat chat = publicChatService.addMembersToPublicChat(dto.getChatId(), adminId, dto.getUserIds());

        Set<UserDto> usersAddedIds = publicChatService.usersCanBeAdd(dto.getChatId(), dto.getUserIds()).stream()
                .map(user -> Mapper.map(user, UserDto.class)).collect(Collectors.toSet());

        PublicChatAddMembersResponseDto responseDto = PublicChatAddMembersResponseDto.builder()
                .chatId(dto.getChatId())
                .userAddedIds(usersAddedIds)
                .adminId(adminId)
                .build();

        Action action = Action.builder()
                .type(ActionType.ADD_NEW_MEMBERS)
                .dto(responseDto).build();

        return getResponse(chat, action);
    }

    @Override
    public ActionResult deleteUserFromPublicChat(Long deleterId, PublicChatDeleteMemberRequestDto dto) {
        PublicChat chat = publicChatService.deleteUserFromPublicChat(dto.getChatId(), deleterId, dto.getUserId());

        User user = findUser(dto.getUserId());
        User deleter = findUser(deleterId);

        PublicChatDeleteMemberResponseDto responseDto = PublicChatDeleteMemberResponseDto.builder()
                .chatId(chat.getId())
                .user(Mapper.map(user, UserDto.class))
                .deleter(Mapper.map(deleter, UserDto.class))
                .build();

        Action action = Action.builder()
                .type(ActionType.DELETE_MEMBER)
                .dto(responseDto).build();

        return getResponse(chat, action);
    }

    @Override
    public ActionResult editProfilePublicChat(Long editorId, PublicChatEditProfileRequestDto dto) {
        PublicChat chat1 = Mapper.map(dto.getChat(), PublicChat.class);
        PublicChat chat = publicChatService.editProfilePublicChat(chat1, editorId);
        PublicChatEditProfileResponseDto responseDto = PublicChatEditProfileResponseDto.builder()
                .chat(Mapper.map(chat, ChatDto.class))
                .build();
        Action action = Action.builder()
                .type(ActionType.EDIT_PROFILE_PUBLIC_CHAT)
                .dto(responseDto).build();
        Set<Long> receivers = publicChatService.getChatMembers(chat.getId()).stream().map(User::getId).collect(Collectors.toSet());
        return ActionResult.builder()
                .action(action)
                .receivers(receivers).build();
    }

    @Override
    public ActionResult createPublicChat(Long creatorId, PublicChatCreateRequestDto dto) {
        PublicChat chatSend = Mapper.map(dto.getChat(), PublicChat.class);
        PublicChat chat = publicChatService.createPublicChat(creatorId, chatSend, dto.getInitMemberIds());
        PublicChatCreateResponseDto responseDto = PublicChatCreateResponseDto.builder()
                .chat(Mapper.map(chat, ChatDto.class))
                .build();
        Action action = Action.builder()
                .type(ActionType.CREATE_PUBLIC_CHAT)
                .dto(responseDto).build();
        Set<Long> receivers = publicChatService.getChatMembers(chat.getId()).stream().map(User::getId).collect(Collectors.toSet());
        return ActionResult.builder()
                .action(action)
                .receivers(receivers).build();
    }

    @Override
    public ActionResult getPublicChatMembers(Long userId, PublicChatGetMembersRequestDto dto) {
        Set<UserDto> users = publicChatService.getChatMembers(dto.getChatId()).stream()
                .map(user -> Mapper.map(user, UserDto.class)).collect(Collectors.toSet());

        Set<MemberDto> members = users.stream()
                .map(user -> MemberDto.builder()
                        .chatId(dto.getChatId())
                        .userId(user.getId())
                        .build())
                .collect(Collectors.toSet());

        PublicChatGetMembersResponseDto responseDto = PublicChatGetMembersResponseDto.builder()
                .users(users)
                .members(members)
                .build();

        Action action = Action.builder()
                .type(ActionType.GET_PUBLIC_CHAT_MEMBERS)
                .dto(responseDto)
                .build();

        return ActionResult.builder()
                .action(action)
                .receivers(Set.of(userId)).build();
    }

    private ActionResult getResponse(PublicChat chat, Action action) {
        Set<Long> receivers = new HashSet<>();

        switch (chat.getType()) {
            case GROUP -> receivers.addAll(publicChatService.getChatMembers(chat.getId())
                    .stream().map(User::getId).toList());

            case CHANNEL -> {
                receivers.add(chat.getOwner().getId());
                receivers.addAll(userRepository.findAdminsByChatId(chat.getId()).stream()
                        .map(User::getId).toList());
            }
        }

        return ActionResult.builder()
                .action(action)
                .receivers(receivers).build();
    }

    @Override
    public ActionResult leavePublicChat(Long userId, PublicChatLeaveRequestDto dto) {
        PublicChat chat = publicChatService.leavePublicChat(dto.getChatId(), userId);

        PublicChatLeaveResponseDto responseDto = PublicChatLeaveResponseDto.builder()
                .chatId(chat.getId())
                .user(Mapper.map(findUser(userId), UserDto.class))
                .build();

        Action action = Action.builder()
                .type(ActionType.LEAVE_PUBLIC_CHAT)
                .dto(responseDto).build();

        return getResponse(chat, action);
    }

    @Override
    public ActionResult selectNewAdminPublicChat(Long selectorId, PublicChatSelectNewAdminRequestDto dto) {
        PublicChat chat = publicChatService.selectNewAdminPublicChat(dto.getChatId(), selectorId, dto.getUserId());
        PublicChatSelectNewAdminResponseDto responseDto = PublicChatSelectNewAdminResponseDto.builder()
                .chatId(chat.getId())
                .userId(dto.getUserId())
                .build();
        Action action = Action.builder()
                .type(ActionType.SELECT_NEW_ADMIN_PUBLIC_CHAT)
                .dto(responseDto)
                .build();
        return getResponse(chat, action);
    }

    @Override
    public ActionResult deleteAdminPublicChat(Long selectorId, PublicChatDeleteAdminRequestDto dto) {
        PublicChat chat = publicChatService.deleteAdminPublicChat(dto.getChatId(), selectorId, dto.getUserId());
        PublicChatDeleteAdminResponseDto responseDto = PublicChatDeleteAdminResponseDto.builder()
                .chatId(chat.getId())
                .userId(dto.getUserId())
                .build();
        Action action = Action.builder()
                .type(ActionType.DELETE_ADMIN_PUBLIC_CHAT)
                .dto(responseDto)
                .build();
        return getResponse(chat, action);
    }

    private User findUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
    }

}
