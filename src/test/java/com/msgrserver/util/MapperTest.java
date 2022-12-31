package com.msgrserver.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.msgrserver.action.Action;
import com.msgrserver.action.ActionType;
import com.msgrserver.model.dto.user.UserGetChatsResponseDto;
import com.msgrserver.model.entity.chat.ChatType;
import com.msgrserver.model.entity.chat.PrivateChat;
import com.msgrserver.model.entity.chat.PublicChat;
import com.msgrserver.model.entity.message.TextMessage;
import com.msgrserver.model.entity.user.User;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class MapperTest {

    private final Mapper mapper;

    @Test
    void mapTest() {
    }

    @Test
    void toJsonAndFromJsonTest() throws JsonProcessingException {
        // arrange
        var pv = createPV("ali", "mohammad");
        var group = createGroup("Ali's Group");
        var channel = createChannel("Ali's Channel");

        pv.setMessages(Set.of(createMessage("salam")));
        group.setAdmins(Set.of(createUser("mahsa")));
        channel.setMembers(Set.of(pv.getUser1(), pv.getUser2()));
        channel.setOwner(createUser("sorour"));

        var dto = UserGetChatsResponseDto.builder()
                .chats(new HashSet<>(List.of(pv, group, channel)))
                .build();

        var expectedAction = Action.builder()
                .type(ActionType.GET_USER_CHATS)
                .dto(dto)
                .build();

        // act
        String json = mapper.toJson(expectedAction);

        var actualAction = mapper.fromJson(json, Action.class);

        // assert
        assertEquals(expectedAction.toString(), actualAction.toString());
    }

    private User createUser(String name) {
        return User.builder().firstName(name).build();
    }

    private TextMessage createMessage(String text) {
        return TextMessage.builder().text(text).build();
    }

    private PrivateChat createPV(String user1Name, String user2Name) {
        return PrivateChat.builder().user1(createUser(user1Name)).user2(createUser(user2Name)).type(ChatType.PRIVATE).build();
    }

    private PublicChat createGroup(String title) {
        return PublicChat.builder().title(title).type(ChatType.GROUP).build();
    }

    private PublicChat createChannel(String title) {
        return PublicChat.builder().title(title).type(ChatType.CHANNEL).build();

    }
}