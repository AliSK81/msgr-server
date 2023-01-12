package com.msgrserver.handler.user;

import com.msgrserver.action.ActionResult;
import com.msgrserver.model.dto.user.request.UserGetChatsRequestDto;
import com.msgrserver.model.dto.user.request.UserSignInRequestDto;
import com.msgrserver.model.dto.user.request.UserSignUpRequestDto;

public interface UserHandler {

    ActionResult signUp(UserSignUpRequestDto dto);

    ActionResult signIn(UserSignInRequestDto dto);

    ActionResult getUserChats(UserGetChatsRequestDto dto);

}
