package com.msgrserver.service;

import com.msgrserver.model.entity.chat.Chat;
import com.msgrserver.model.entity.message.BinaryMessage;
import com.msgrserver.model.entity.message.Message;
import com.msgrserver.model.entity.message.TextMessage;

import java.util.Set;

public interface ChatService {
    Chat sendText(Long chatId, Long senderId, TextMessage textMessage);

    Chat sendFile(Long chatId, BinaryMessage binaryMessage);

}
