package com.msgrserver.service;

import com.msgrserver.model.entity.chat.Chat;
import com.msgrserver.model.entity.message.BinaryMessage;
import com.msgrserver.model.entity.message.TextMessage;

public interface MessageService {

    TextMessage sendText(Long chatId, TextMessage textMessage);

    BinaryMessage sendFile(Long chatId, BinaryMessage binaryMessage);

}
