package com.msgrserver.service;


import com.msgrserver.exception.BadRequestException;
import com.msgrserver.exception.ChatNotFoundException;
import com.msgrserver.exception.UserNotFoundException;
import com.msgrserver.model.entity.chat.PublicChat;
import com.msgrserver.model.entity.message.BinaryMessage;
import com.msgrserver.model.entity.message.TextMessage;
import com.msgrserver.model.entity.user.User;
import com.msgrserver.repository.MessageRepository;
import com.msgrserver.repository.PublicChatRepository;
import com.msgrserver.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class MessageServiceImpl implements MessageService {
    private final PublicChatRepository publicChatRepository;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    @Override
    public TextMessage saveText(Long chatId, TextMessage textMessage) {

        var sender = findUser(textMessage.getSenderId());

        var Chat = findChat(chatId);


        if (chatId < 0) {

            chatId = -chatId;

            var chat = findChat(chatId);

//            if (!chat.getMembers().contains(sender)) {
//                throw new BadRequestException();
//            }

            // todo check sender is a chat participant
            // todo check chat type is channel and sender is an admin

        } else {
            var user = findUser(chatId);

            // todo check sender is not blocked by receiver user
        }

        textMessage.setDateTime(LocalDateTime.now());

        return messageRepository.save(textMessage);
    }

    @Override
    public BinaryMessage saveFile(BinaryMessage binaryMessage) {
        return null;
    }

    private PublicChat findChat(Long chatId) {
        return publicChatRepository.findById(chatId).orElseThrow(ChatNotFoundException::new);
    }

    private User findUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }

}
