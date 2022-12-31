package com.msgrserver.handler;

import com.msgrserver.action.Response;
import com.msgrserver.model.dto.user.UserGetChatsRequestDto;
import com.msgrserver.model.dto.user.UserGetChatsResponseDto;
import com.msgrserver.model.dto.user.UserSignInRequestDto;
import com.msgrserver.model.dto.user.UserSignUpRequestDto;

public interface UserHandler {

    Response signUp(UserSignUpRequestDto dto);

    Response signIn(UserSignInRequestDto dto);

    Response getUserChats(UserGetChatsRequestDto dto);

}
