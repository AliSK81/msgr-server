package com.msgrserver.action.handler.user;

import com.msgrserver.action.Action;
import com.msgrserver.action.ActionResult;
import com.msgrserver.action.ActionType;
import com.msgrserver.action.handler.ActionHandler;
import com.msgrserver.model.dto.user.UserSignUpRequestDto;
import com.msgrserver.model.dto.user.UserSignUpResponseDto;
import com.msgrserver.model.entity.user.User;
import com.msgrserver.model.entity.user.UserSession;
import com.msgrserver.service.user.SessionService;
import com.msgrserver.service.user.UserService;
import com.msgrserver.util.Mapper;
import com.msgrserver.util.TokenGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class UserSignUpHandler implements ActionHandler<UserSignUpRequestDto> {
    private final UserService userService;
    private final SessionService sessionService;

    @Override
    public ActionType type() {
        return ActionType.SIGN_UP;
    }

    @Override
    public ActionResult handle(Long userId, UserSignUpRequestDto dto) {

        User mappedUser = Mapper.map(dto, User.class);
        User newUser = userService.saveUser(mappedUser);

        UserSession session = UserSession.builder()
                .id(TokenGenerator.generateNewToken())
                .user(newUser)
                .build();

        sessionService.saveUserSession(session);

        UserSignUpResponseDto responseDto = UserSignUpResponseDto.builder()
                .userId(newUser.getId())
                .build();

        Action action = Action.builder()
                .type(type())
                .token(session.getId())
                .dto(responseDto).build();

        Set<Long> receivers = Set.of(newUser.getId());

        return ActionResult.builder()
                .action(action)
                .receivers(receivers).build();
    }
}
