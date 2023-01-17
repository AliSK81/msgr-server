package com.msgrserver.handler.user;

import com.msgrserver.action.Action;
import com.msgrserver.action.ActionResult;
import com.msgrserver.action.ActionType;
import com.msgrserver.model.dto.chat.ChatDto;
import com.msgrserver.model.dto.message.MessageDto;
import com.msgrserver.model.dto.user.*;
import com.msgrserver.model.entity.chat.Chat;
import com.msgrserver.model.entity.chat.PrivateChat;
import com.msgrserver.model.entity.chat.PublicChat;
import com.msgrserver.model.entity.user.User;
import com.msgrserver.model.entity.user.UserSession;
import com.msgrserver.service.message.MessageService;
import com.msgrserver.service.user.SessionService;
import com.msgrserver.service.user.UserService;
import com.msgrserver.util.Mapper;
import com.msgrserver.util.TokenGenerator;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserHandlerImpl implements UserHandler {

    private final UserService userService;
    private final MessageService messageService;
    private final SessionService sessionService;

    @Override
    public ActionResult signUp(UserSignUpRequestDto dto) {
        User newUser = userService.saveUser(
                Mapper.map(dto, User.class)
        );

        UserSession session = UserSession.builder()
                .id(TokenGenerator.generateNewToken())
                .user(newUser)
                .build();

        sessionService.saveUserSession(session);

        UserSignUpResponseDto responseDto = UserSignUpResponseDto.builder()
                .userId(newUser.getId())
                .build();

        Action action = Action.builder()
                .type(ActionType.SIGN_UP)
                .token(session.getId())
                .dto(responseDto).build();

        Set<Long> receivers = Set.of(newUser.getId());

        return ActionResult.builder()
                .user(newUser)
                .action(action)
                .receivers(receivers).build();
    }

    @Override
    public ActionResult signIn(UserSignInRequestDto dto) {

        User user = userService.findUser(dto.getUsername(), dto.getPassword());

        UserSession session = UserSession.builder()
                .id(TokenGenerator.generateNewToken())
                .user(user)
                .build();

        sessionService.saveUserSession(session);

        UserSignInResponseDto responseDto = UserSignInResponseDto.builder()
                .userId(user.getId())
                .build();

        Action action = Action.builder()
                .type(ActionType.SIGN_IN)
                .token(session.getId())
                .dto(responseDto).build();

        Set<Long> receivers = Set.of(user.getId());

        return ActionResult.builder()
                .user(user)
                .action(action)
                .receivers(receivers).build();
    }

    @Override
    public ActionResult getUserChats(Long userId) {
        UserGetChatsResponseDto responseDto = generateGetUserChatsResponse(userId);

        Action action = Action.builder()
                .type(ActionType.GET_USER_CHATS)
                .dto(responseDto).build();

        return ActionResult.builder()
                .action(action)
                .receivers(Set.of(userId)).build();
    }

    @Override
    public ActionResult editProfile(Long userId, UserEditProfileRequestDto dto) {
        User userSend = Mapper.map(dto, User.class);
        userSend.setAllowedInvite(dto.isAccessAddPublicChat());
        userSend.setVisibleAvatar(dto.isVisibleAvatar());
        User user = userService.editProfile(userSend, userId);
        UserEditProfileResponseDto responseDto = UserEditProfileResponseDto.builder()
                .userDto(Mapper.map(user, UserDto.class))
                .accessAddPublicChat(user.isAllowedInvite())
                .visibleAvatar(user.isVisibleAvatar())
                .build();
        Action action = Action.builder()
                .type(ActionType.EDIT_PROFILE)
                .dto(responseDto)
                .build();
        Set<Long> receivers = new HashSet<>(List.of(dto.getUserDto().getId()));
        return ActionResult.builder()
                .action(action)
                .receivers(receivers).build();
    }

    @Override
    public ActionResult getUserProfile(Long viewerId, UserViewProfileRequestDto dto) {
        User user = userService.findUser(dto.getUsername());

        UserDto userDto = Mapper.map(user, UserDto.class);
        if (!user.isVisibleAvatar()) {
            userDto.setAvatar(null);
        }

        UserViewProfileResponseDto responseDto = UserViewProfileResponseDto.builder()
                .user(userDto)
                .build();

        Action action = Action.builder()
                .type(ActionType.VIEW_USER_PROFILE)
                .dto(responseDto)
                .build();

        Set<Long> receivers = Set.of(viewerId);

        return ActionResult.builder()
                .action(action)
                .receivers(receivers).build();
    }

    UserGetChatsResponseDto generateGetUserChatsResponse(Long userId) {
        Set<ChatDto> chats = new HashSet<>();
        Set<UserDto> users = new HashSet<>();
        Set<MessageDto> messages = new HashSet<>();

        userService.getUserChats(userId).forEach(chat -> {

            ChatDto chatDto = Mapper.map(chat, ChatDto.class);
            chats.add(chatDto);

            if (chat instanceof PrivateChat privateChat) {
                User user = privateChat.getParticipant(userId);
                chatDto.setUser1Id(userId);
                chatDto.setUser2Id(user.getId());

                UserDto userDto = Mapper.map(user, UserDto.class);
                users.add(userDto);
            }

            var lastMessage = messageService.getLastMessage(chat.getId());

            if (lastMessage != null) {
                if (chat instanceof PublicChat) {
                    UserDto userDto = Mapper.map(lastMessage.getSender(), UserDto.class);
                    users.add(userDto);
                }

                MessageDto messageDto = Mapper.map(lastMessage, MessageDto.class);
                messageDto.setSenderId(lastMessage.getSender().getId());
                messageDto.setChatId(lastMessage.getChat().getId());
                messages.add(messageDto);
            }
        });

        return UserGetChatsResponseDto.builder()
                .chats(chats)
                .users(users)
                .messages(messages)
                .build();
    }

}


