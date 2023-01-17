package com.msgrserver.service.message;

import com.msgrserver.model.entity.message.BinaryMessage;
import com.msgrserver.model.entity.message.Message;
import com.msgrserver.model.entity.message.TextMessage;

public interface MessageService {

    TextMessage saveText(Long chatId, Long senderId, TextMessage textMessage);

    BinaryMessage saveFile(Byte[] data, String name, String caption);

    Byte[] recoveryFile(Long id);

    Message getLastMessage(Long chatId);

}