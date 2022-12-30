package com.msgrserver.model.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.msgrserver.model.dto.chat.ChatReceiveProfileUpdateDto;
import com.msgrserver.model.dto.message.MessageReceiveFileDto;
import com.msgrserver.model.dto.message.MessageReceiveTextDto;
import com.msgrserver.model.dto.message.MessageSendFileDto;
import com.msgrserver.model.dto.message.MessageSendTextDto;
import com.msgrserver.model.dto.user.*;
import lombok.ToString;

@ToString
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME)
@JsonSubTypes(value = {
        @JsonSubTypes.Type(value = UserGetChatsRequestDto.class, name = "UserGetChatsRequestDto"),
        @JsonSubTypes.Type(value = UserGetChatsResponseDto.class, name = "UserGetChatsResponseDto"),
        @JsonSubTypes.Type(value = UserReceiveUpdateDto.class, name = "UserReceiveUpdateDto"),
        @JsonSubTypes.Type(value = UserSignInRequestDto.class, name = "UserSignInRequestDto"),
        @JsonSubTypes.Type(value = UserSignInResponseDto.class, name = "UserSignInResponseDto"),
        @JsonSubTypes.Type(value = UserSignUpRequestDto.class, name = "UserSignUpRequestDto"),
        @JsonSubTypes.Type(value = UserSignUpResponseDto.class, name = "UserSignUpResponseDto"),
        @JsonSubTypes.Type(value = MessageReceiveFileDto.class, name = "MessageReceiveFileDto"),
        @JsonSubTypes.Type(value = MessageReceiveTextDto.class, name = "MessageReceiveTextDto"),
        @JsonSubTypes.Type(value = MessageSendFileDto.class, name = "MessageSendFileDto"),
        @JsonSubTypes.Type(value = MessageSendTextDto.class, name = "MessageSendTextDto"),
        @JsonSubTypes.Type(value = ChatReceiveProfileUpdateDto.class, name = "ChatReceiveProfileUpdateDto"),
        @JsonSubTypes.Type(value = UserGetChatsRequestDto.class, name = "UserGetChatsRequestDto"),
})
public class ActionDto {
}
