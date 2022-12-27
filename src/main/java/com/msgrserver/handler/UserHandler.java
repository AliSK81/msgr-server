package com.msgrserver.handler;

import com.msgrserver.action.Response;
import com.msgrserver.model.dto.user.UserSignUpRequestDto;

public interface UserHandler {

    Response signUp(UserSignUpRequestDto dto);
}
