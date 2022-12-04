package com.msgrserver.service;


import com.msgrserver.exception.ChatNotFoundException;
import com.msgrserver.model.entity.chat.Chat;
import com.msgrserver.model.entity.message.BinaryMessage;
import com.msgrserver.model.entity.message.TextMessage;
import com.msgrserver.repository.ChatRepository;
import com.msgrserver.repository.MessageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class MessageServiceImpl implements MessageService {

    private final ChatRepository chatRepository;

    private final MessageRepository messageRepository;

    @Override
    public TextMessage sendText(Long chatId, TextMessage textMessage) {
        textMessage.setDateTime(LocalDateTime.now());

        var chat = chatRepository.findById(chatId).orElseThrow(ChatNotFoundException::new);
        textMessage.setChat(chat);

        return messageRepository.save(textMessage);
    }

    @Override
    public BinaryMessage sendFile(Long chatId, BinaryMessage binaryMessage) {
        return null;
    }


}
