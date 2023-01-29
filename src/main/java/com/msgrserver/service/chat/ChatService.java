package com.msgrserver.service.chat;

import com.msgrserver.model.entity.chat.Chat;
import com.msgrserver.model.entity.message.Message;

import java.util.Set;

public interface ChatService {

    Chat findChat(Long chatId);

    Set<Message> getChatMessages(Long userId, Long chatId);

    void deleteChat(Long userId, Long chatId);
}
