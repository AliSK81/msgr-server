package com.msgrserver.service;


import com.msgrserver.exception.BadRequestException;
import com.msgrserver.exception.ChatNotFoundException;
import com.msgrserver.exception.UserNotFoundException;
import com.msgrserver.model.entity.chat.Chat;
import com.msgrserver.model.entity.chat.ChatType;
import com.msgrserver.model.entity.chat.PrivateChat;
import com.msgrserver.model.entity.chat.PublicChat;
import com.msgrserver.model.entity.message.BinaryMessage;
import com.msgrserver.model.entity.message.Message;
import com.msgrserver.model.entity.message.TextMessage;
import com.msgrserver.model.entity.user.User;
import com.msgrserver.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Service
public class MessageServiceImpl implements MessageService {
    private final ChatRepository chatRepository;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    @Override
    public TextMessage saveText(Long chatId, Long userId, TextMessage textMessage) {

        User sender = findUser(userId);
        Chat chat = findChat(chatId);

        // todo create private chat if not exist

        if (chat instanceof PublicChat) {
            validatePublicChat(sender, (PublicChat) chat);
        } else if (chat instanceof PrivateChat) {
            validatePrivateChat(sender, (PrivateChat) chat);
        }

        textMessage.setDateTime(LocalDateTime.now());

        return messageRepository.save(textMessage);
    }

    @Override
    public BinaryMessage saveFile(BinaryMessage binaryMessage) {
        return null;
    }

    @Override
    public Message getLastMessage(Long chatId) {
        List<Message> messages = messageRepository.findAll();
        // todo sort by date time
        return messages.stream().sorted().findFirst().get();
    }

    private Chat findChat(Long chatId) {
        return chatRepository.findById(chatId).orElseThrow(ChatNotFoundException::new);
    }

    private User findUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }

    private void validatePublicChat(User sender, PublicChat chat) {
        Set<User> users = userRepository.findUsersByChatsId(chat.getId());
        boolean isMember = users.contains(sender);

        if (!isMember)
            throw new BadRequestException();

        boolean isChannel = chat.getType() == ChatType.CHANNEL;

        if (isChannel) {
            boolean isOwner = chat.getOwner().equals(sender);
            boolean isAdmin = chat.getAdmins().contains(sender);

            if (!isOwner && !isAdmin)
                throw new BadRequestException();
        }

        //todo check sending message is allowed in chat
    }

    private void validatePrivateChat(User sender, PrivateChat chat) {

        // todo check sender is not blocked by receiver user
    }

}
