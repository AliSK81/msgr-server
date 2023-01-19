package com.msgrserver.service.chat;

import com.msgrserver.exception.BadRequestException;
import com.msgrserver.exception.ChatNotFoundException;
import com.msgrserver.model.entity.chat.Chat;
import com.msgrserver.model.entity.chat.PrivateChat;
import com.msgrserver.model.entity.chat.PublicChat;
import com.msgrserver.model.entity.message.Message;
import com.msgrserver.repository.ChatRepository;
import com.msgrserver.repository.MessageRepository;
import com.msgrserver.repository.UserRepository;
import com.msgrserver.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;
    private final MessageRepository messageRepository;
    private final UserService userService;

    @Override
    public Chat findChat(Long chatId) {
        return chatRepository.findById(chatId).orElseThrow(ChatNotFoundException::new);
    }

    @Override
    public Set<Message> getChatMessages(Long chatId) {
        return messageRepository.findMessagesByChatId(chatId);
    }

    @Override
    public boolean isChatParticipant(Long chatId, Long userId) {
        Set<Chat> chats = userService.getUserChats(userId);
        return chats.stream()
                .map(Chat::getId)
                .collect(Collectors.toSet())
                .contains(chatId);
    }

    @Transactional
    @Override
    public void deleteChat(Long userId, Long chatId) {
        Chat chat = findChat(chatId);

        if (chat instanceof PrivateChat privateChat) {
            boolean isUserInChat =
                    Set.of(privateChat.getUser1().getId(), privateChat.getUser2().getId()).contains(userId);

            if (!isUserInChat) {
                throw new BadRequestException();
            }

        } else if (chat instanceof PublicChat publicChat) {

            if (!publicChat.getOwner().getId().equals(userId)) {
                throw new BadRequestException();
            }

            publicChat.setAdmins(new HashSet<>());
            publicChat.setMembers(new HashSet<>());
            chatRepository.save(publicChat);
        }

        messageRepository.deleteMessagesByChatId(chatId);
        chatRepository.deleteById(chatId);
    }
}

