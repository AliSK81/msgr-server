package com.msgrserver.service.message;


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
import com.msgrserver.repository.ChatRepository;
import com.msgrserver.repository.MessageRepository;
import com.msgrserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final ChatRepository chatRepository;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    @Override
    public TextMessage createText(Long chatId, Long senderId, TextMessage textMessage) {

        User sender = findUser(senderId);
        Chat chat = findChat(chatId);

        // todo create private chat if not exist

        if (chat instanceof PublicChat) {
            checkPublicChatAccess(sender, (PublicChat) chat);
        } else if (chat instanceof PrivateChat) {
            checkPrivateChatAccess(sender, (PrivateChat) chat);
        }

        textMessage.setSender(sender);
        textMessage.setChat(chat);

        textMessage.setDate(System.currentTimeMillis());
        textMessage.setId(null);

        return messageRepository.save(textMessage);
    }

    @Override
    public BinaryMessage saveFile(BinaryMessage binaryMessage) {
        return null;
    }

    @Override
    public Message getLastMessage(Long chatId) {
        return messageRepository.getLastMessageByChatId(chatId);
    }

    private Chat findChat(Long chatId) {
        return chatRepository.findById(chatId).orElseThrow(ChatNotFoundException::new);
    }

    private User findUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }

    private void checkPublicChatAccess(User sender, PublicChat chat) {
        Set<User> users = userRepository.findMembersByChatId(chat.getId());
        boolean isMember = users.stream()
                .map(User::getId)
                .collect(Collectors.toSet())
                .contains(sender.getId());

        if (!isMember)
            throw new BadRequestException();

        boolean isChannel = chat.getType() == ChatType.CHANNEL;

        if (isChannel) {
            Set<User> admins = userRepository.findAdminsByChatId(chat.getId());

            boolean isOwner = chat.getOwner().equals(sender);
            boolean isAdmin = admins.contains(sender);

            if (!isOwner && !isAdmin)
                throw new BadRequestException();
        }

        //todo check sending message is allowed in chat
    }

    private void checkPrivateChatAccess(User sender, PrivateChat chat) {

        // todo check sender is not blocked by receiver user
    }

}
