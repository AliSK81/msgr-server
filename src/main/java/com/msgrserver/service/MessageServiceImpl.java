package com.msgrserver.service;


import com.msgrserver.exception.BadRequestException;
import com.msgrserver.exception.ChatNotFoundException;
import com.msgrserver.exception.UserNotFoundException;
import com.msgrserver.model.entity.chat.Chat;
import com.msgrserver.model.entity.chat.ChatType;
import com.msgrserver.model.entity.chat.PrivateChat;
import com.msgrserver.model.entity.chat.PublicChat;
import com.msgrserver.model.entity.message.BinaryMessage;
import com.msgrserver.model.entity.message.TextMessage;
import com.msgrserver.model.entity.user.User;
import com.msgrserver.repository.ChatRepository;
import com.msgrserver.repository.MessageRepository;
import com.msgrserver.repository.PrivateChatRepository;
import com.msgrserver.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class MessageServiceImpl implements MessageService {
    private final ChatRepository chatRepository;
    private final PrivateChatRepository privateChatRepository;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    @Override
    public TextMessage saveText(Long chatId, TextMessage textMessage) {

        User sender = findUser(textMessage.getSenderId());

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

    private Chat findChat(Long chatId) {
        return chatRepository.findById(chatId).orElseThrow(ChatNotFoundException::new);
    }

    private User findUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }

    private void validatePublicChat(User sender, PublicChat chat) {
        boolean isMember = chat.getMembers().contains(sender);
        boolean isOwner = chat.getOwner().equals(sender);
        boolean isAdmin = chat.getAdmins().contains(sender);
        boolean isChannel = chat.getType() == ChatType.CHANNEL;
        boolean isValidMsg = !isMember || isChannel && !isOwner && !isAdmin;

        if (!isValidMsg) {
            throw new BadRequestException();
        }
    }

    private void validatePrivateChat(User sender, PrivateChat chat) {

        // todo check sender is not blocked by receiver user
    }

}
