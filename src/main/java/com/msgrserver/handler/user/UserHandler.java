package com.msgrserver.handler.user;

import com.msgrserver.action.ActionResult;
import com.msgrserver.model.dto.user.UserEditProfileRequestDto;
import com.msgrserver.model.dto.user.UserViewProfileRequestDto;
import com.msgrserver.model.dto.user.UserSignInRequestDto;
import com.msgrserver.model.dto.user.UserSignUpRequestDto;

public interface UserHandler {

    ActionResult signUp(UserSignUpRequestDto dto);

    ActionResult signIn(UserSignInRequestDto dto);

    ActionResult getUserChats(Long userId);

    ActionResult editProfile(Long userId, UserEditProfileRequestDto dto);

    ActionResult getUserProfile(Long viewerId, UserViewProfileRequestDto dto);
}
