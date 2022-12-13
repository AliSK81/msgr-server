package com.msgrserver.service;

import com.msgrserver.model.entity.message.BinaryMessage;
import com.msgrserver.model.entity.message.TextMessage;

public interface MessageService {

    TextMessage saveText(TextMessage textMessage);

    BinaryMessage saveFile(BinaryMessage binaryMessage);

}
