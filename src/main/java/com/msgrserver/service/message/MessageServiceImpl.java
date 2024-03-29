package com.msgrserver.service.message;


import com.msgrserver.exception.BadRequestException;
import com.msgrserver.exception.ChatNotFoundException;
import com.msgrserver.exception.MessageNotFoundException;
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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final ChatRepository chatRepository;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final MemberRepository memberRepository;
    private final AdminRepository adminRepository;

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

        boolean isMember = memberRepository.findByChatIdAndUserId(chat.getId(), sender.getId()).isPresent();

        if (!isMember)
            throw new BadRequestException();

        boolean isChannel = chat.getType() == ChatType.CHANNEL;

        if (isChannel) {

            boolean isOwner = chat.getOwner().equals(sender);
            boolean isAdmin = adminRepository.findByChatIdAndUserId(chat.getId(), sender.getId()).isPresent();

            if (!isOwner && !isAdmin)
                throw new BadRequestException();
        }

        //todo check sending message is allowed in chat
    }

    private void checkPrivateChatAccess(User sender, PrivateChat chat) {

        // todo check sender is not blocked by receiver user
    }

    @Transactional
    @Override
    public Long deleteMessage(Long deleterId, Long messageId) {
        Message message = findMessage(messageId);
        User sender = message.getSender();
        Chat chat = message.getChat();
        User deleter = findUser(deleterId);

        if (chat instanceof PrivateChat) {
            if (!deleter.equals(sender)) {
                throw new BadRequestException();
            }
        } else if (chat instanceof PublicChat publicChat) {
            User owner = publicChat.getOwner();
            if (!deleter.equals(owner) && !deleter.equals(sender)) {
                throw new BadRequestException();
            }
        }
        messageRepository.deleteMessageById(messageId);
        return chat.getId();
    }

    @Override
    public Message findMessage(Long messageId) {
        return messageRepository.findById(messageId).orElseThrow(MessageNotFoundException::new);
    }
}
