package com.msgrserver.action.handler.user;

import com.msgrserver.action.Action;
import com.msgrserver.action.ActionResult;
import com.msgrserver.action.ActionType;
import com.msgrserver.action.handler.ActionHandler;
import com.msgrserver.model.dto.user.UserDto;
import com.msgrserver.model.dto.user.UserViewProfileRequestDto;
import com.msgrserver.model.dto.user.UserViewProfileResponseDto;
import com.msgrserver.model.entity.user.User;
import com.msgrserver.service.user.UserService;
import com.msgrserver.util.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class UserGetProfileHandler implements ActionHandler<UserViewProfileRequestDto> {
    private final UserService userService;

    @Override
    public ActionType type() {
        return ActionType.VIEW_USER_PROFILE;
    }

    @Override
    public ActionResult handle(Long userId, UserViewProfileRequestDto dto) {

        User user = userService.findUser(dto.getUsername());

        UserDto userDto = Mapper.map(user, UserDto.class);
        if (!user.getVisibleAvatar()) {
            userDto.setAvatar(null);
        }

        UserViewProfileResponseDto responseDto = UserViewProfileResponseDto.builder()
                .user(userDto)
                .build();

        Action action = Action.builder()
                .type(type())
                .dto(responseDto)
                .build();

        Set<Long> receivers = Set.of(userId);

        return ActionResult.builder()
                .action(action)
                .receivers(receivers).build();
    }
}
