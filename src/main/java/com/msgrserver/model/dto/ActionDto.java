package com.msgrserver.model.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.msgrserver.model.dto.chat.*;
import com.msgrserver.model.dto.message.*;
import com.msgrserver.model.dto.user.*;
import lombok.ToString;

@ToString
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME)
@JsonSubTypes(value = {
        @JsonSubTypes.Type(value = UserDto.class, name = "UserDto"),
        @JsonSubTypes.Type(value = UserGetChatsRequestDto.class, name = "UserGetChatsRequestDto"),
        @JsonSubTypes.Type(value = UserGetChatsResponseDto.class, name = "UserGetChatsResponseDto"),
        @JsonSubTypes.Type(value = UserReceiveUpdateDto.class, name = "UserReceiveUpdateDto"),
        @JsonSubTypes.Type(value = UserSignInRequestDto.class, name = "UserSignInRequestDto"),
        @JsonSubTypes.Type(value = UserSignInResponseDto.class, name = "UserSignInResponseDto"),
        @JsonSubTypes.Type(value = UserSignUpRequestDto.class, name = "UserSignUpRequestDto"),
        @JsonSubTypes.Type(value = UserSignUpResponseDto.class, name = "UserSignUpResponseDto"),
        @JsonSubTypes.Type(value = MessageDto.class, name = "MessageDto"),
        @JsonSubTypes.Type(value = MessageReceiveFileDto.class, name = "MessageReceiveFileDto"),
        @JsonSubTypes.Type(value = MessageReceiveTextDto.class, name = "MessageReceiveTextDto"),
        @JsonSubTypes.Type(value = MessageSendFileDto.class, name = "MessageSendFileDto"),
        @JsonSubTypes.Type(value = MessageSendTextDto.class, name = "MessageSendTextDto"),
        @JsonSubTypes.Type(value = ChatDto.class, name = "ChatDto"),
        @JsonSubTypes.Type(value = ChatReceiveProfileUpdateDto.class, name = "ChatReceiveProfileUpdateDto"),
        @JsonSubTypes.Type(value = ChatGetPrivateProfileRequestDto.class, name = "ChatGetPrivateProfileRequestDto"),
        @JsonSubTypes.Type(value = ChatGetPrivateProfileResponseDto.class, name = "ChatGetPrivateProfileResponseDto"),
        @JsonSubTypes.Type(value = ChatGetPublicProfileRequestDto.class, name = "ChatGetPublicProfileRequestDto"),
        @JsonSubTypes.Type(value = ChatGetPublicProfileResponseDto.class, name = "ChatGetPublicProfileResponseDto")
})
public class ActionDto {
}
