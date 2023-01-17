package com.msgrserver.service.chat;

import com.msgrserver.exception.ChatNotFoundException;
import com.msgrserver.model.entity.chat.Chat;
import com.msgrserver.model.entity.message.Message;
import com.msgrserver.repository.ChatRepository;
import com.msgrserver.repository.MessageRepository;
import com.msgrserver.repository.PublicChatRepository;
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
    private final PublicChatRepository publicChatRepository;
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
}
