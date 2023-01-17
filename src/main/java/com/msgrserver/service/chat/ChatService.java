package com.msgrserver.service.chat;

import com.msgrserver.model.entity.chat.Chat;

public interface ChatService {
    Chat findChat(Long chatId);
}
