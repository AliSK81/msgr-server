package com.msgrserver.handler.user;

import com.msgrserver.action.ActionResult;
import com.msgrserver.model.dto.user.*;

public interface UserHandler {

    ActionResult signUp(UserSignUpRequestDto dto);

    ActionResult signIn(UserSignInRequestDto dto);

    ActionResult getUserChats(UserGetChatsRequestDto dto);

    ActionResult getUserProfile(UserGetProfileRequestDto dto);
}
