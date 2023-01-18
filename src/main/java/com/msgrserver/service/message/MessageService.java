package com.msgrserver.service.message;

import com.msgrserver.model.entity.message.BinaryMessage;
import com.msgrserver.model.entity.message.Message;
import com.msgrserver.model.entity.message.TextMessage;

public interface MessageService {

    TextMessage createText(Long chatId, Long senderId, TextMessage textMessage);

    BinaryMessage saveFile(BinaryMessage binaryMessage);

    Message getLastMessage(Long chatId);

    void deleteMessage(Long deleterId, Long messageId);

    Message findMessage(Long messageId);
}
