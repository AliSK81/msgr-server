package com.msgrserver.handler;

import com.msgrserver.action.Action;
import com.msgrserver.action.ActionType;
import com.msgrserver.action.Response;
import com.msgrserver.model.dto.user.*;
import com.msgrserver.model.entity.chat.Chat;
import com.msgrserver.model.entity.user.User;
import com.msgrserver.service.UserService;
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

    @Override
    public Response signUp(UserSignUpRequestDto dto) {
        User newUser = userService.saveUser(
                Mapper.map(dto, User.class)
        );

        UserSignUpResponseDto responseDto = UserSignUpResponseDto.builder()
                .userId(newUser.getId())
                .token(TokenGenerator.generateNewToken()).build();

        Action action = Action.builder()
                .type(ActionType.SIGN_UP)
                .dto(responseDto).build();

        Set<Long> receivers = new HashSet<>(List.of(newUser.getId()));

        return Response.builder()
                .action(action)
                .receivers(receivers).build();
    }

    @Override
    public Response signIn(UserSignInRequestDto dto) {

        User user = userService.findUser(dto.getUsername(), dto.getPassword());

        UserSignInResponseDto responseDto = UserSignInResponseDto.builder()
                .userId(user.getId())
                .token(TokenGenerator.generateNewToken()).build();

        Action action = Action.builder()
                .type(ActionType.SIGN_IN)
                .dto(responseDto).build();

        Set<Long> receivers = new HashSet<>(List.of(user.getId()));

        return Response.builder()
                .action(action)
                .receivers(receivers).build();
    }

    @Override
    public Response getUserChats(UserGetChatsRequestDto dto) {
        Set<Chat> chats = userService.getUserChats(dto.getUserId());
        UserGetChatsResponseDto responseDto = UserGetChatsResponseDto.builder()
//                .chats(chats)
                .build();
        Action action = Action.builder()
                .type(ActionType.GET_USER_CHATS)
                .dto(responseDto).build();

        Set<Long> receivers = new HashSet<>(List.of(dto.getUserId()));

        return Response.builder()
                .action(action)
                .receivers(receivers).build();
    }
}


