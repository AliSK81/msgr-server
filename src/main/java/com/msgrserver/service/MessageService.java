package com.msgrserver.service;

import com.msgrserver.model.entity.message.BinaryMessage;
import com.msgrserver.model.entity.message.Message;
import com.msgrserver.model.entity.message.TextMessage;

public interface MessageService {

    TextMessage saveText(Long chatId, Long senderId, TextMessage textMessage);

    BinaryMessage saveFile(BinaryMessage binaryMessage);

    Message getLastMessage(Long chatId);

}
