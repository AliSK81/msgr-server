package com.msgrserver.service.chat;

import com.msgrserver.model.entity.chat.Chat;
import com.msgrserver.model.entity.message.Message;

import java.util.Set;

public interface ChatService {

    Chat findChat(Long chatId);

    Set<Message> getChatMessages(Long chatId);

    boolean isChatParticipant(Long chatId, Long userId);

    void deleteChat(Long userId, Long chatId);
}
