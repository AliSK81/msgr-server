package com.msgrserver.action;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.msgrserver.model.dto.user.UserSignUpRequestDto;
import com.msgrserver.util.Mapper;
import org.junit.jupiter.api.Test;

class ActionTest {

    @Test
    void getDto() throws JsonProcessingException {

        UserSignUpRequestDto dto = UserSignUpRequestDto.builder()
                .username("user")
                .password("pass")
                .build();

        Action action = Action.builder()
                .type(ActionType.SIGN_UP)
                .dto(dto)
                .build();

        String json = Mapper.toJson(action);
        System.out.println(json);

        Action action1 = Mapper.fromJson(json, Action.class);
        System.out.println(Mapper.toJson(action));

    }
}