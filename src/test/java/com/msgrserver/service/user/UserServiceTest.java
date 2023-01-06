package com.msgrserver.service.user;

import com.msgrserver.model.dto.chat.ChatDto;
import com.msgrserver.model.dto.message.MessageDto;
import com.msgrserver.model.dto.user.UserDto;
import com.msgrserver.model.entity.chat.Chat;
import com.msgrserver.model.entity.chat.ChatType;
import com.msgrserver.model.entity.chat.PrivateChat;
import com.msgrserver.model.entity.chat.PublicChat;
import com.msgrserver.model.entity.message.Message;
import com.msgrserver.model.entity.message.MessageType;
import com.msgrserver.model.entity.message.TextMessage;
import com.msgrserver.model.entity.user.User;
import com.msgrserver.util.Mapper;
import org.junit.jupiter.api.Test;

import java.util.Set;

class UserServiceTest {

    @Test
    void findUser() {
    }

    @Test
    void saveUser() {
    }

    @Test
    void deleteUser() {
    }

    @Test
    void getUserChats() {

        Set<ChatDto> chatDtos = Set.of(
                ChatDto.builder().id(1L).lastMessage(
                        MessageDto.builder().id(1L).sender(
                                UserDto.builder().id(1L)
                                        .build()).build()).build()
        );

        chatDtos.forEach(chatDto -> {
            Chat chat = null;
            if(chat.getType() == ChatType.PRIVATE) {
                chat = Mapper.map(chatDto, PublicChat.class);
            } else {
                chat = Mapper.map(chat, PrivateChat.class);
            }

            User sender = Mapper.map(chatDto.getLastMessage().getSender(), User.class);

            Message message = null;
            if(chatDto.getLastMessage().getMessageType() == MessageType.TEXT) {
                message = Mapper.map(chatDto.getLastMessage(), TextMessage.class);
                message.setChat(chat);
                message.setSender(sender);
            }

        });

    }

    @Test
    void testFindUser() {
    }
}