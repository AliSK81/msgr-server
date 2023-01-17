package com.msgrserver.service.chat;

import com.msgrserver.exception.BadRequestException;
import com.msgrserver.exception.ChatNotFoundException;
import com.msgrserver.exception.UserNotFoundException;
import com.msgrserver.model.entity.chat.Chat;
import com.msgrserver.model.entity.chat.PrivateChat;
import com.msgrserver.model.entity.chat.PublicChat;
import com.msgrserver.model.entity.message.Message;
import com.msgrserver.model.entity.user.User;
import com.msgrserver.repository.ChatRepository;
import com.msgrserver.repository.MessageRepository;
import com.msgrserver.repository.PublicChatRepository;
import com.msgrserver.repository.UserRepository;
import com.msgrserver.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    @Override
    public void deleteChat(Long userId, Long chatId) {
        Chat chat = findChat(chatId);
        if (chat instanceof PrivateChat) {
            boolean isUserInChat = ((PrivateChat) chat).getUser1().getId().equals(userId) || ((PrivateChat) chat).getUser2().getId().equals(userId);
            if (!isUserInChat)
                throw new BadRequestException();
        } else if (chat instanceof PublicChat) {
            if (!(((PublicChat) chat).getOwner().getId().equals(userId)))
                throw new BadRequestException();
        }
        messageRepository.deleteMessagesByChatId(chatId);
        chatRepository.deleteById(chatId);
    }
}
