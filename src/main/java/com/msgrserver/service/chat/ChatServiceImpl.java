package com.msgrserver.service.chat;

import com.msgrserver.exception.ChatNotFoundException;
import com.msgrserver.model.entity.chat.Chat;
import com.msgrserver.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;

    @Override
    public Chat findChat(Long chatId) {
        return chatRepository.findById(chatId).orElseThrow(ChatNotFoundException::new);
    }
}
