package com.msgrserver.action.handler.user;

import com.msgrserver.action.Action;
import com.msgrserver.action.ActionResult;
import com.msgrserver.action.ActionType;
import com.msgrserver.action.handler.ActionHandler;
import com.msgrserver.model.dto.user.UserSignInRequestDto;
import com.msgrserver.model.dto.user.UserSignInResponseDto;
import com.msgrserver.model.entity.user.User;
import com.msgrserver.model.entity.user.UserSession;
import com.msgrserver.service.user.SessionService;
import com.msgrserver.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class UserSignInHandler implements ActionHandler<UserSignInRequestDto> {
    private final UserService userService;
    private final SessionService sessionService;

    @Override
    public ActionType type() {
        return ActionType.SIGN_IN;
    }

    @Override
    public ActionResult handle(Long userId, UserSignInRequestDto dto) {

        User user = userService.findUser(dto.getUsername(), dto.getPassword());

        UserSession session = sessionService.createUserSession(user);

        UserSignInResponseDto responseDto = UserSignInResponseDto.builder()
                .userId(user.getId())
                .build();

        Action action = Action.builder()
                .type(type())
                .token(session.getId())
                .dto(responseDto).build();

        Set<Long> receivers = Set.of(user.getId());

        return ActionResult.builder()
                .action(action)
                .receivers(receivers).build();
    }
}
