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
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
                .id(user.getId())
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
        Set<Chat> chats = userService.getUserChats(userId);

        Set<ChatDto> chatDtos = convert(userId, chats);

        UserGetChatsResponseDto responseDto = UserGetChatsResponseDto.builder()
                .chats(chatDtos).build();
        Action action = Action.builder()
                .type(ActionType.GET_USER_CHATS)
                .dto(responseDto).build();

        Set<Long> receivers = Set.of(userId);

        return ActionResult.builder()
                .action(action)
                .receivers(receivers).build();
    }

    @Override
    public ActionResult editProfile(Long userId, UserEditProfileRequestDto dto) {
        User userSend = Mapper.map(dto, User.class);
        userSend.setAccessAddPublicChat(dto.getAccessAddPublicChat());
        userSend.setVisibleAvatar(dto.getVisibleAvatar());
        User user = userService.editProfile(userSend, userId);
        UserEditProfileResponseDto responseDto = UserEditProfileResponseDto.builder()
                .userDto(Mapper.map(user, UserDto.class))
                .accessAddPublicChat(user.getAccessAddPublicChat())
                .visibleAvatar(user.getVisibleAvatar())
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
    public ActionResult getUserProfile(UserGetProfileRequestDto dto) {
        User user = userService.getProfile(dto.getUsername());
        UserDto userDto = Mapper.map(user, UserDto.class);
        if (!(user.getVisibleAvatar()))
            userDto.setAvatar(null);
        UserGetProfileResponseDto responseDto = UserGetProfileResponseDto.builder()
                .userDto(userDto)
                .build();

        Action action = Action.builder()
                .type(ActionType.GET_USER_PROFILE)
                .dto(responseDto)
                .build();
        Set<Long> receivers = new HashSet<>(List.of(dto.getUserId()));
        return ActionResult.builder()
                .action(action)
                .receivers(receivers).build();
    }

    private Set<ChatDto> convert(Long senderId, Set<Chat> chats) {
        Set<ChatDto> chatDtos = new HashSet<>();

        for (Chat chat : chats) {

            var lastMessage = messageService.getLastMessage(chat.getId());
            // todo
            var lastMessageDto = MessageDto.builder().build();

            ChatDto chatDto = ChatDto.builder()
                    .id(chat.getId())
                    .lastMessage(lastMessageDto)
                    .chatType(chat.getType())
                    .build();

            if (chat instanceof PrivateChat privateChat) {
                User receiver = privateChat.getParticipant(senderId);
                chatDto.setAvatar(receiver.getAvatar());
                chatDto.setTitle(receiver.getName());

            } else if (chat instanceof PublicChat publicChat) {
                chatDto.setAvatar(publicChat.getAvatar());
                chatDto.setTitle(publicChat.getTitle());
            }

            chatDtos.add(chatDto);
        }

        return chatDtos;
    }
}


