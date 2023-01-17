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

import java.time.LocalDateTime;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final ChatRepository chatRepository;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    @Override
    public TextMessage saveText(Long chatId, Long senderId, TextMessage textMessage) {

        User sender = findUser(senderId);
        Chat chat = findChat(chatId);

        // todo create private chat if not exist

        if (chat instanceof PublicChat) {
            checkPublicChatAccess(sender, (PublicChat) chat);
        } else if (chat instanceof PrivateChat) {
            checkPrivateChatAccess(sender, (PrivateChat) chat);
        }

        textMessage.setDateTime(LocalDateTime.now());

        return messageRepository.save(textMessage);
    }

    @Override
    public BinaryMessage saveFile(Byte[] data, String name, String caption) {
        BinaryMessage binaryMessage = BinaryMessage.builder()
                .name(name)
                .caption(caption)
                .data(data).build();
        return messageRepository.save(binaryMessage);
    }

    @Override
    public Byte[] recoveryFile(Long id) {
        Message message = messageRepository.findMessageById(id);

        if (message instanceof BinaryMessage) {
            return ((BinaryMessage) message).getData();
        } else throw new BadRequestException();
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
        Set<User> users = userRepository.findUsersByChatId(chat.getId());
        boolean isMember = users.contains(sender);

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
