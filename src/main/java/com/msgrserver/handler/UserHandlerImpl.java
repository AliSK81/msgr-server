package com.msgrserver.handler;

import com.msgrserver.action.Action;
import com.msgrserver.action.ActionType;
import com.msgrserver.action.Response;
import com.msgrserver.model.dto.user.UserSignUpRequestDto;
import com.msgrserver.model.dto.user.UserSignUpResponseDto;
import com.msgrserver.model.entity.user.User;
import com.msgrserver.service.UserService;
import com.msgrserver.util.Generator;
import com.msgrserver.util.Mapper;
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
                .token(Generator.generateNewToken()).build();

        Action action = Action.builder()
                .type(ActionType.SIGN_UP)
                .dto(responseDto).build();

        Set<Long> receivers = new HashSet<>(List.of(newUser.getId()));

        return Response.builder()
                .action(action)
                .receivers(receivers).build();
    }
}


