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
import com.msgrserver.service.chat.ChatService;
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

    private final ChatService chatService;

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

        Set<Long> receivers = publicChatService.getChatMembers(chat.getId()).stream()
                .map(User::getId).collect(Collectors.toSet());

        return ActionResult.builder()
                .action(action)
                .receivers(receivers)
                .build();
    }

    @Override
    public ActionResult addMembersToPublicChat(Long adder, PublicChatAddMembersRequestDto dto) {
        PublicChat chat = publicChatService.addMembersToPublicChat(dto.getChatId(), adder, dto.getUserIds());

        Set<UserDto> users = publicChatService.usersCanBeAdd(dto.getChatId(), dto.getUserIds()).stream()
                .map(user -> Mapper.map(user, UserDto.class)).collect(Collectors.toSet());

        PublicChatAddMembersResponseDto responseDto = PublicChatAddMembersResponseDto.builder()
                .chat(Mapper.map(chat, ChatDto.class))
                .users(users)
                .adder(Mapper.map(findUser(adder), UserDto.class))
                .build();

        Action action = Action.builder()
                .type(ActionType.ADD_NEW_MEMBERS)
                .dto(responseDto)
                .build();

        Set<Long> receivers = publicChatService.getChatMembers(dto.getChatId()).stream()
                .map(User::getId).collect(Collectors.toSet());

        return ActionResult.builder()
                .action(action)
                .receivers(receivers)
                .build();
    }

    @Override
    public ActionResult deleteUserFromPublicChat(Long deleterId, PublicChatDeleteMemberRequestDto dto) {

        Set<Long> receivers = publicChatService.getChatMembers(dto.getChatId()).stream()
                .map(User::getId).collect(Collectors.toSet());

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

        return ActionResult.builder()
                .action(action)
                .receivers(receivers)
                .build();
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

        Set<AdminDto> admins = publicChatService.getChatAdmins(dto.getChatId()).stream()
                .map(user -> AdminDto.builder()
                        .chatId(dto.getChatId())
                        .userId(user.getId())
                        .build())
                .collect(Collectors.toSet());

        PublicChatGetMembersResponseDto responseDto = PublicChatGetMembersResponseDto.builder()
                .users(users)
                .members(members)
                .admins(admins)
                .build();

        Action action = Action.builder()
                .type(ActionType.GET_PUBLIC_CHAT_MEMBERS)
                .dto(responseDto)
                .build();

        return ActionResult.builder()
                .action(action)
                .receivers(Set.of(userId)).build();
    }

    private ActionResult getResult(Long userId, PublicChat chat, Action action) {
        Set<Long> receivers = new HashSet<>();

        switch (chat.getType()) {
            case GROUP -> receivers.addAll(publicChatService.getChatMembers(chat.getId())
                    .stream().map(User::getId).collect(Collectors.toSet()));

            case CHANNEL -> {
                receivers.add(chat.getOwner().getId());
                receivers.addAll(userRepository.findAdminsByChatId(chat.getId()).stream()
                        .map(User::getId).collect(Collectors.toSet()));
            }
        }
        receivers.add(userId);

        return ActionResult.builder()
                .action(action)
                .receivers(receivers).build();
    }

    @Override
    public ActionResult leavePublicChat(Long userId, PublicChatLeaveRequestDto dto) {
        Set<Long> receivers = publicChatService.getChatMembers(dto.getChatId()).stream()
                .map(User::getId).collect(Collectors.toSet());

        PublicChat chat = publicChatService.findPublicChat(dto.getChatId());

        if (chat.getOwner().getId().equals(userId)) {
            chatService.deleteChat(userId, dto.getChatId());
        } else {
            publicChatService.leavePublicChat(dto.getChatId(), userId);
        }

        PublicChatLeaveResponseDto responseDto = PublicChatLeaveResponseDto.builder()
                .chatId(chat.getId())
                .user(Mapper.map(findUser(userId), UserDto.class))
                .build();

        Action action = Action.builder()
                .type(ActionType.LEAVE_PUBLIC_CHAT)
                .dto(responseDto).build();

        return ActionResult.builder()
                .action(action)
                .receivers(receivers)
                .build();
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

        Set<Long> receivers = publicChatService.getChatMembers(dto.getChatId()).stream()
                .map(User::getId).collect(Collectors.toSet());

        return ActionResult.builder()
                .action(action)
                .receivers(receivers)
                .build();
    }

    @Override
    public ActionResult deleteAdminPublicChat(Long deleterId, PublicChatDeleteAdminRequestDto dto) {
        PublicChat chat = publicChatService.deleteAdminPublicChat(dto.getChatId(), deleterId, dto.getUserId());

        PublicChatDeleteAdminResponseDto responseDto = PublicChatDeleteAdminResponseDto.builder()
                .chatId(chat.getId())
                .userId(dto.getUserId())
                .build();

        Action action = Action.builder()
                .type(ActionType.DELETE_ADMIN_PUBLIC_CHAT)
                .dto(responseDto)
                .build();

        Set<Long> receivers = publicChatService.getChatMembers(dto.getChatId()).stream()
                .map(User::getId).collect(Collectors.toSet());

        return ActionResult.builder()
                .action(action)
                .receivers(receivers)
                .build();
    }

    private User findUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
    }

}
