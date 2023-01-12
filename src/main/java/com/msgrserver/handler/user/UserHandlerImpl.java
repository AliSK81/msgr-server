package com.msgrserver.handler.user;

import com.msgrserver.action.Action;
import com.msgrserver.action.ActionResult;
import com.msgrserver.action.ActionType;
import com.msgrserver.model.dto.chat.ChatDto;
import com.msgrserver.model.dto.message.MessageDto;
import com.msgrserver.model.dto.user.request.UserGetChatsRequestDto;
import com.msgrserver.model.dto.user.request.UserSignInRequestDto;
import com.msgrserver.model.dto.user.request.UserSignUpRequestDto;
import com.msgrserver.model.dto.user.response.UserGetChatsResponseDto;
import com.msgrserver.model.dto.user.response.UserSignInResponseDto;
import com.msgrserver.model.dto.user.response.UserSignUpResponseDto;
import com.msgrserver.model.entity.chat.Chat;
import com.msgrserver.model.entity.chat.PrivateChat;
import com.msgrserver.model.entity.chat.PublicChat;
import com.msgrserver.model.entity.user.User;
import com.msgrserver.service.message.MessageService;
import com.msgrserver.service.user.UserService;
import com.msgrserver.util.Mapper;
import com.msgrserver.util.TokenGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class UserHandlerImpl implements UserHandler {

    private final UserService userService;
    private final MessageService messageService;

    @Override
    public ActionResult signUp(UserSignUpRequestDto dto) {
        User newUser = userService.saveUser(
                Mapper.map(dto, User.class)
        );

        UserSignUpResponseDto responseDto = UserSignUpResponseDto.builder()
                .userId(newUser.getId())
                .token(TokenGenerator.generateNewToken()).build();

        Action action = Action.builder()
                .type(ActionType.SIGN_UP)
                .dto(responseDto).build();

        Set<Long> receivers = new HashSet<>(List.of(newUser.getId()));

        return ActionResult.builder()
                .action(action)
                .receivers(receivers).build();
    }

    @Override
    public ActionResult signIn(UserSignInRequestDto dto) {

        User user = userService.findUser(dto.getUsername(), dto.getPassword());

        UserSignInResponseDto responseDto = UserSignInResponseDto.builder()
                .userId(user.getId())
                .token(TokenGenerator.generateNewToken()).build();

        Action action = Action.builder()
                .type(ActionType.SIGN_IN)
                .dto(responseDto).build();

        Set<Long> receivers = new HashSet<>(List.of(user.getId()));

        return ActionResult.builder()
                .action(action)
                .receivers(receivers).build();
    }

    @Override
    public ActionResult getUserChats(UserGetChatsRequestDto dto) {
        Set<Chat> chats = userService.getUserChats(dto.getUserId());

        Set<ChatDto> chatDtos = convert(dto.getUserId(), chats);

        UserGetChatsResponseDto responseDto = UserGetChatsResponseDto.builder()
                .chats(chatDtos).build();
        Action action = Action.builder()
                .type(ActionType.GET_USER_CHATS)
                .dto(responseDto).build();

        Set<Long> receivers = new HashSet<>(List.of(dto.getUserId()));

        return ActionResult.builder()
                .action(action)
                .receivers(receivers).build();
    }

    private Set<ChatDto> convert(Long senderId, Set<Chat> chats) {
        Set<ChatDto> chatDtos = new HashSet<>();

        for (Chat chat : chats) {

            var lastMessage = messageService.getLastMessage(chat.getId());
            var lastMessageDto = Mapper.map(lastMessage, MessageDto.class);

            ChatDto chatDto = ChatDto.builder()
                    .id(chat.getId())
                    .lastMessage(lastMessageDto)
                    .type(chat.getType())
                    .build();

            if (chat instanceof PrivateChat privateChat) {
                User receiver = privateChat.getParticipant(senderId);
                chatDto.setAvatar(receiver.getAvatar());
                chatDto.setTitle(receiver.getName());

            } else if (chat instanceof PublicChat publicChat) {
                chatDto.setAvatar(publicChat.getAvatar());
                chatDto.setTitle(publicChat.getTitle());
            }

            chatDtos.add(chatDto);
        }

        return chatDtos;
    }
}


