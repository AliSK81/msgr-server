package com.msgrserver.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.msgrserver.action.Action;
import com.msgrserver.action.ActionType;
import com.msgrserver.model.dto.user.response.UserSignInResponseDto;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

@SpringBootTest
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class MapperTest {

    @Test
    void mapTest() {
    }

    @Test
    void toJsonAndFromJsonTest() throws JsonProcessingException {
        // arrange
        var dto = UserSignInResponseDto.builder().userId(1L).token("token").build();

        var expectedAction = Action.builder()
                .type(ActionType.GET_USER_CHATS)
                .dto(dto)
                .token("test-token").build();

        // act
        String json = Mapper.toJson(expectedAction);

        var actualAction = Mapper.fromJson(json, Action.class);

        // assert
        assertEquals(expectedAction, actualAction);
        assertInstanceOf(expectedAction.getDto().getClass(), actualAction.getDto());
    }

}